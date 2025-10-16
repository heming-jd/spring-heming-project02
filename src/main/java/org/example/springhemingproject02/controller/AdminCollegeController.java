package org.example.springhemingproject02.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.springhemingproject02.component.JWTComponent;
import org.example.springhemingproject02.dox.*;
import org.example.springhemingproject02.service.ApplicationService;
import org.example.springhemingproject02.service.CollegeService;
import org.example.springhemingproject02.service.NodeService;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admincollege/")
@RequiredArgsConstructor
public class AdminCollegeController {
    private final ApplicationService applicationService;
    private final JWTComponent jwtComponent;
    private final NodeService nodeService;
    private final CollegeService collegeService;
    private final Userservice userService;
    //增删改查大类 专业 增删改查节点 查询本学院学生 查询大类学生 (包括学生 分数排名 与待审核数等)   根据学生id查提交 根据申请id查文件 增删改查评价
    @GetMapping("category")
    public ResultVO getCategories(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        User user = userService.getUserbyId(uid);
        Long collegeId = user.getCollegeId();
        return collegeService.getCategories(collegeId);
    }
    @PostMapping("category")
    public ResultVO addCategory(@RequestBody Category category) {
        return collegeService.addcategory(category);
    }
    @PutMapping("category")
    public ResultVO updateCategory(@RequestBody Category category){
        return collegeService.updatecategory(category);
    }
    @DeleteMapping("category")
    public ResultVO deleteCategory(@PathVariable String id) {
        return collegeService.deletecategory(id);
    }
    @GetMapping("majors/{categoryId}")
    public ResultVO getmajors(@PathVariable Long categoryId) {return collegeService.getmajors(categoryId);}
    @PostMapping("major")
    public ResultVO addmajor(@RequestBody Major major) {
        return collegeService.addmajor(major);
    }
    @PutMapping("major")
    public ResultVO updateMajor(@RequestBody Major major){
        return collegeService.updatemajor(major);
    }
    @DeleteMapping("major")
    public ResultVO deletemajor(@PathVariable Long id) {
        return collegeService.deletemajor(id);
    }
    @PostMapping("node")
    public ResultVO addNode(@RequestBody Node node) {
        return nodeService.addNode(node);
    }
    @GetMapping("nodes/{categoryId}")
    public ResultVO getfirstNodes(@PathVariable Long categoryId) {
        return nodeService.getfirstNodes(categoryId);
    }
    @GetMapping("nodes/{id}")
    public ResultVO getNodes(@PathVariable Long id) {
        return nodeService.getNodes(id);
    }
    @DeleteMapping("node/{id}")
    public ResultVO deleteNode(@PathVariable Long id) {
        Optional<Node> Id = Optional.ofNullable(nodeService.findById(id));
        if (Id.isPresent()) {
            nodeService.deleteNode(id);
            return ResultVO.ok();
        }
        else {
            return ResultVO.error(400 ,"Node not found");
        }
    }
    @GetMapping("students")
    public ResultVO getStudentsByTeacher(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        User user = userService.getUserbyId(uid);
        Long collegeId = user.getCollegeId();
        return userService.getStudentsByTeacher(collegeId);
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
    @PutMapping("review")
    public ResultVO updateReview(@RequestBody Review review){
        return applicationService.updateReview(review);
    }
    @GetMapping("review/{applicationId}")
    public ResultVO getReviewByApplicationId(@PathVariable Long applicationId){
        return applicationService.getReviewByApplicationId(applicationId);
    }
    @DeleteMapping("review/{applicationId}")
    public ResultVO deleteReviewByApplicationId(@PathVariable Long applicationId){
        return applicationService.deleteReviewByApplicationId(applicationId);
    }
    @GetMapping("applications/{studentId}")
    public ResultVO getapplicationsbystudentId(@PathVariable Long id){
        return applicationService.getapplicationsbystudentId(id);
    }
}
