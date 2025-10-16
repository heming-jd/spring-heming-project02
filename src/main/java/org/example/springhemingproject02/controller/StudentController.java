package org.example.springhemingproject02.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.springhemingproject02.component.JWTComponent;
import org.example.springhemingproject02.dox.*;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.service.ApplicationService;
import org.example.springhemingproject02.service.NodeService;
import org.example.springhemingproject02.service.ScoreService;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/")
@RequiredArgsConstructor
public class StudentController {
    private final NodeService nodeService;
    private final ApplicationService applicationService;
    private final ScoreService scoreService;
    private final Userservice userService;
    private final JWTComponent jwtComponent;
    //查分与专排 查一级节点 节点后代  增删改查提交与提交文件(增加申请时需要限项处理)
    //查项目获得分数
    @GetMapping("score")
    public ResultVO getscore(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        return scoreService.getscore(uid);
    }
    @GetMapping("firstnodes/{categoryId}")
    public ResultVO getfirstNodes(@PathVariable Long categoryId) {
        return nodeService.getfirstNodes(categoryId);
    }
    @GetMapping("nodes/{id}")
    public ResultVO getNodes(@PathVariable Long id) {
        return nodeService.getNodes(id);
    }
    @PostMapping("application")
    public ResultVO addapplication(@RequestBody Application application, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        application.setStudentId(uid);
        application.setStatus(Application.STATUS_COMMIT);
        return applicationService.addApplication(application);
    }
    @DeleteMapping("application/{id}")
    public ResultVO deleteApplication(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();

        // 先查询记录，验证当前用户是否有权限删除
        Application application = applicationService.getApplicationById(id);
        if (application == null || !application.getStudentId().equals(uid)) {
        return ResultVO.error(Code.FORBIDDEN);
        }
        return applicationService.deleteApplication(id);
    }
    @PutMapping("application")
    public ResultVO updateApplication(@RequestBody Application application, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        // 验证权限：只能更新自己的申请
        Application existingApp = applicationService.getApplicationById(application.getId());
        if (existingApp == null || !existingApp.getStudentId().equals(uid)) {
            return ResultVO.error(Code.FORBIDDEN);
        }
        // 确保不能修改学生ID
        application.setStudentId(uid);
        application.setStatus(Application.STATUS_COMMIT);
        return applicationService.updateApplication(application);
    }
    @GetMapping("applications")
    public ResultVO getallapplication() {
        return applicationService.getallapplication();
    }
    @GetMapping("applications/{id}")
    public ResultVO getapplication(@PathVariable Long id) {
        return applicationService.getapplication(id);
    }


    @PostMapping("applicationfiles")
    public ResultVO addaddapplicationfiles(@RequestBody ApplicationFile applicationFile, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        applicationFile.setStudentId(uid);
        return applicationService.addApplicationFile(applicationFile);
    }

    @DeleteMapping("applicationfiles/{id}")
    public ResultVO deleteApplicationFile(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();

        //验证权限
        ApplicationFile file = applicationService.getApplicationFileById(id);
        if (file == null || !file.getStudentId().equals(uid)) {
        return ResultVO.error(Code.FORBIDDEN);
        }
        return applicationService.deleteApplicationFile(id);
    }

    @PutMapping("applicationfiles")
    public ResultVO updateApplicationFile(@RequestBody ApplicationFile applicationFile, HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        // 验证权限：只能更新自己的文件
        ApplicationFile existingFile = applicationService.getApplicationFileById(applicationFile.getId());
        if (existingFile == null || !existingFile.getStudentId().equals(uid)) {
            return ResultVO.error(Code.FORBIDDEN);
        }
        // 确保不能修改学生ID
        applicationFile.setStudentId(uid);
        return applicationService.updateApplicationFile(applicationFile);
    }
    @GetMapping("applicationfiles/{applicationId}")
    public ResultVO getapplicationfiles(@PathVariable Long applicationId) {
        return applicationService.getapplicationfilesbyapplicationId(applicationId);

    }
    @GetMapping("totalscore")
    public ResultVO gettotalscore(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        User user = userService.getUserbyId(uid);
        return ResultVO.builder()
                .code(200)
                .data(scoreService.calculateStudentScore(uid,user.getCategoryId()))
                .build();
    }
}
