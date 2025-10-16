package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.Application;
import org.example.springhemingproject02.dox.ApplicationFile;
import org.example.springhemingproject02.dox.Node;
import org.example.springhemingproject02.dox.Review;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.repository.ApplicationFileRepository;
import org.example.springhemingproject02.repository.ApplicationRepository;
import org.example.springhemingproject02.repository.NodeRepository;
import org.example.springhemingproject02.repository.ReviewRepository;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationFileRepository applicationFileRepository;
    private final ReviewRepository reviewRepository;
    private final NodeRepository nodeRepository;
    public ResultVO addApplication(Application application) {
        // 1. 检查节点限项
        Node leafNode = nodeRepository.findById(application.getLeafNodeId());
        if (leafNode == null) {
            return ResultVO.error(400, "节点不存在");
        }
        // 2. 找到所有需要检查限项的祖先节点
        List<Node> limitNodes = nodeRepository.findAncestorNodesWithLimit(leafNode.getId());
        // 3. 对每个有限项的祖先节点进行检查
        for (Node limitNode : limitNodes) {
            if (limitNode.getLimitCount() > 0) {
                long currentCount = applicationRepository.countByStudentAndNodeAncestor(
                        application.getStudentId(), limitNode.getId());

                if (currentCount >= limitNode.getLimitCount()) {
                    return ResultVO.error(400,
                            String.format("节点【%s】已达到最大申请数量限制(%d)",
                                    limitNode.getNodeName(), limitNode.getLimitCount()));
                }
            }
        }
        // 4. 保存申请
        applicationRepository.save(application);
        return ResultVO.ok();
    }

    public ResultVO addApplicationFile(ApplicationFile applicationFile) {
        applicationFileRepository.save(applicationFile);
        return ResultVO.ok();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public ResultVO deleteApplication(Long id) {
        //删除时应先删掉附属文件
        applicationFileRepository.deletejoin(id);
        applicationRepository.deleteById(id);
        return ResultVO.ok();
    }

    public ApplicationFile getApplicationFileById(Long id) {
        return applicationFileRepository.findById(id);
    }

    public ResultVO deleteApplicationFile(Long id) {
        applicationFileRepository.deleteById(id);
        return ResultVO.ok();
    }

    public ResultVO updateApplication(Application application) {
        applicationRepository.save(application);
        return ResultVO.ok();
    }

    public ResultVO updateApplicationFile(ApplicationFile applicationFile) {
        applicationFileRepository.save(applicationFile);
        return ResultVO.ok();
    }

    //添加通过评价修改对应申请表状态
    public ResultVO addReview(Review review) {
        if(review.getStatus()=="wedwa"){
            reviewRepository.save(review);
            return ResultVO.ok();
        }
        Application application = applicationRepository.findById(review.getApplicationId());
        if(application == null) return ResultVO.error(Code.BAD_REQUEST);
        application.setStatus(Application.STATUS_SUCCESS);
        applicationRepository.save(application);
        reviewRepository.save(review);
        return ResultVO.ok();
    }

    public ResultVO deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return ResultVO.ok();
    }

    public ResultVO updateReview(Review review) {
        reviewRepository.save(review);
        return ResultVO.ok();
    }

    public ResultVO getapplication(Long id) {
        return ResultVO.success(applicationRepository.findById(id));
    }

    public ResultVO getapplicationfilesbyapplicationId(Long id) {
        List<ApplicationFile> appfs = applicationFileRepository.findByApplicationId(id);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("apps", appfs))
                .build();
    }

    public ResultVO getallapplication() {
        return ResultVO.builder()
                .code(200)
                .data(Map.of("apps", applicationRepository.findAll()))
                .build();
    }

    public ResultVO getReviewByApplicationId(Long applicationId) {
        return ResultVO.success(reviewRepository.findByApplicationId(applicationId));
    }

    public ResultVO getapplicationsbystudentId(Long id) {
        return ResultVO.builder()
                .code(200)
                .data(Map.of("apps", applicationRepository.findByStudentId(id)))
                .build();
    }

    public ResultVO getapplicationstatusbystudentId(Long id) {
        return ResultVO.builder()
                .code(200)
                .data(Map.of("commit", applicationRepository.countStatus1ByStudentId(id),"success", applicationRepository.countStatus2ByStudentId(id)))
                .build();
    }

    public ResultVO getfilebyapplicationid(Long applicationId) {
        return ResultVO.builder()
                .code(200)
                .data(Map.of("appfs", applicationFileRepository.findByApplicationId(applicationId)))
                .build();
    }

    public ResultVO deleteReviewByApplicationId(Long applicationId) {
        reviewRepository.deleteByApplicationId(applicationId);
        return ResultVO.ok();
    }


}
