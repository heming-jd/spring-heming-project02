package org.example.springhemingproject02.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.springhemingproject02.component.JWTComponent;
import org.example.springhemingproject02.dox.Review;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.repository.ApplicationRepository;
import org.example.springhemingproject02.service.ApplicationService;
import org.example.springhemingproject02.service.CollegeService;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/")
@RequiredArgsConstructor
public class TeacherController {
    private final ApplicationService applicationService;
    private final CollegeService collegeService;
    private final Userservice userService;
    private final JWTComponent jwtComponent;
    //查询本学院学生 查询本学院的大类 查询大类学生 (包括学生 分数排名 与待审核数等)   根据学生id查提交 根据申请id查文件 增查评价
    @GetMapping("students")
    public ResultVO getStudentsByTeacher(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        User user = userService.getUserbyId(uid);
        Long collegeId = user.getCollegeId();
        return ResultVO.success(userService.getStudentsByTeacher(collegeId));
    }
    @GetMapping("students/{categoryId}")
    public ResultVO getstudentsbycategory(@PathVariable Long categoryId){
        return userService.getStudentsBycategory(categoryId);
    }
    @GetMapping("files/{applicationId}")
    public ResultVO getfilebyapplicationid(@PathVariable Long applicationId){
        return applicationService.getfilebyapplicationid(applicationId);
    }
    @PostMapping("review")
    public ResultVO addReview(@RequestBody Review review){
        return applicationService.addReview(review);
    }
    @GetMapping("review/{applicationId}")
    public ResultVO getReviewByApplicationId(@PathVariable Long applicationId){
        return applicationService.getReviewByApplicationId(applicationId);
    }
    @GetMapping("applications/{studentId}")
    public ResultVO getapplicationsbystudentId(@PathVariable Long id){
        return applicationService.getapplicationsbystudentId(id);
    }

}
