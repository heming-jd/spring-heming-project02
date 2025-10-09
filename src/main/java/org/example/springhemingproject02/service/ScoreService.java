package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.Application;
import org.example.springhemingproject02.dox.Node;
import org.example.springhemingproject02.dox.Review;
import org.example.springhemingproject02.dox.StudentScore;
import org.example.springhemingproject02.repository.*;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreService {
    private final StudentScoreRepository studentScoreRepository;
    private final ApplicationRepository applicationRepository;
    private final ReviewRepository reviewRepository;
    private final NodeRepository nodeRepository;
    private final NodeClosureRepository nodeClosureRepository;
    public ResultVO addStudentScore(StudentScore studentScore) {
        studentScoreRepository.save(studentScore);
        return ResultVO.ok();
    }

    public ResultVO getscore(String uid) {
        return ResultVO.builder()
                .code(200)
                .data(studentScoreRepository.findBystudentId(uid))
                .build();
    }
    public BigDecimal calculateStudentScore(String studentId, String categoryId) {
        // 1. 获取学生在大类下的已审核通过申请
        List<Application> applications = applicationRepository.findByStudentIdAndCategory(studentId, categoryId);
        if (applications.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 2. 批量获取申请分数
        Map<String, BigDecimal> applicationScores = getApplicationScores(applications);

        // 3. 处理限分计算
        return calculateScoreWithLimits(studentId, applications, applicationScores);
    }

    /**
     * 批量获取申请分数
     */
    private Map<String, BigDecimal> getApplicationScores(List<Application> applications) {
        List<String> applicationIds = applications.stream()
                .map(Application::getId)
                .collect(Collectors.toList());

        List<Object[]> reviewScores = reviewRepository.findApprovedScoresByApplicationIds(applicationIds);

        Map<String, BigDecimal> applicationScores = new HashMap<>();
        for (Object[] reviewScore : reviewScores) {
            String appId = (String) reviewScore[0];
            BigDecimal score = (BigDecimal) reviewScore[1];
            if (score != null) {
                applicationScores.put(appId, score);
            }
        }
        return applicationScores;
    }

    /**
     * 考虑限分计算最终得分
     */
    private BigDecimal calculateScoreWithLimits(String studentId,
                                                List<Application> applications,
                                                Map<String, BigDecimal> applicationScores) {
        BigDecimal totalScore = BigDecimal.ZERO;
        Map<String, BigDecimal> nodeUsedScores = new HashMap<>();

        for (Application application : applications) {
            String leafNodeId = application.getLeafNodeId();
            BigDecimal originalScore = applicationScores.get(application.getId());

            if (originalScore == null) {
                continue;
            }

            // 找到最近的有限分祖先节点
            String maxScoreNodeId = nodeClosureRepository.findNearestMaxScoreAncestor(leafNodeId);

            if (maxScoreNodeId == null) {
                // 没有限分节点，直接累加
                totalScore = totalScore.add(originalScore);
            } else {
                // 处理限分
                Node maxScoreNode = nodeRepository.findById(maxScoreNodeId).orElse(null);
                if (maxScoreNode != null) {
                    BigDecimal maxScore = maxScoreNode.getMaxScore();
                    BigDecimal currentUsed = nodeUsedScores.getOrDefault(maxScoreNodeId, BigDecimal.ZERO);

                    // 计算可用的分数
                    BigDecimal availableScore = maxScore.subtract(currentUsed);
                    BigDecimal effectiveScore = originalScore.min(availableScore.max(BigDecimal.ZERO));

                    totalScore = totalScore.add(effectiveScore);
                    nodeUsedScores.put(maxScoreNodeId, currentUsed.add(effectiveScore));
                }
            }
        }

        log.info("学生 {} 最终得分: {}", studentId, totalScore);
        return totalScore;
    }

    /**
     * 计算学生在指定节点（包括所有后代节点）下的总分数
     */
    public BigDecimal calculateNodeTotalScore(String studentId, String nodeId) {
        // 获取该节点的所有后代节点ID
        List<String> descendantNodeIds = nodeClosureRepository.findDescendantIdsByAncestorId(nodeId);
        descendantNodeIds.add(nodeId); // 包括自身

        // 查询学生在这些节点下的所有申请
        List<Application> applications = applicationRepository.findByStudentIdAndLeafNodeIds(studentId, descendantNodeIds);

        // 获取这些申请的审核分数
        Map<String, BigDecimal> applicationScores = getApplicationScores(applications);

        // 计算总分数
        BigDecimal totalScore = BigDecimal.ZERO;
        for (Application app : applications) {
            BigDecimal score = applicationScores.get(app.getId());
            if (score != null) {
                totalScore = totalScore.add(score);
            }
        }
        return totalScore;
    }

}
