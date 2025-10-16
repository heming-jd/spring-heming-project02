package org.example.springhemingproject02.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.component.JWTComponent;
import org.example.springhemingproject02.dox.StudentScore;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.dto.StudentRegister;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.service.CollegeService;
import org.example.springhemingproject02.service.ScoreService;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/open/")
@RequiredArgsConstructor
public class OpenController {
    private final Userservice userService;
    private final CollegeService collegeService;
    private final ScoreService scoreService;
    private final PasswordEncoder encoder;
    private final JWTComponent jwtComponent;
    //登录 学生老师注册 获取所有学院 获取所有专业 获取所有类别
    @PostMapping("login")
    public ResultVO postLogin(@RequestBody User loginUser, HttpServletResponse response) {
        User u = userService.getUser(loginUser.getAccount());
        if (u == null || !encoder.matches(loginUser.getPassword(), u.getPassword())) {
            return ResultVO.error(Code.LOGIN_ERROR);
        }
        String result = jwtComponent.encode(Map.of("uid", u.getId(), "role", u.getRole()));
        response.addHeader("token", result);
        response.addHeader("role", u.getRole());
        return ResultVO.success(u);
    }
    @PostMapping("register/student")
    public ResultVO postRegisterstudent(@RequestBody StudentRegister studentRegister) {
        User user = studentRegister.getUser();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_USER);
        userService.addUser(user);
        StudentScore studentScore = studentRegister.getStudentScore();
        studentScore.setStudentId(user.getId());
        scoreService.addStudentScore(studentScore);
        return ResultVO.ok();
    }
    @PostMapping("register/teacher")
    public ResultVO postRegisterteacher(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_Teacher);
        userService.addUser(user);
        return ResultVO.ok();
    }
    @GetMapping("user")
    public ResultVO getUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT decode = jwtComponent.decode(token);
        Long uid = decode.getClaim("uid").asLong();
        User user = userService.getUserbyId(uid);
        return ResultVO.success(user);
    }
    @GetMapping("colleges")
    public ResultVO getAllcolleges() {return collegeService.getAllcollege();}
    @GetMapping("categories/{collegeId}")
    public ResultVO getCategories(@PathVariable Long collegeId) {
        return collegeService.getCategories(collegeId);}
    @GetMapping("majors/{categoryId}")
    public ResultVO getmajors(@PathVariable Long categoryId) {return collegeService.getmajors(categoryId);}
}
