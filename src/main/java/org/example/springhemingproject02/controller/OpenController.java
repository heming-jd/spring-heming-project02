package org.example.springhemingproject02.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.component.JWTComponent;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/open/")
@RequiredArgsConstructor
public class OpenController {
    private final Userservice userService;
    private final PasswordEncoder encoder;
    private final JWTComponent jwtComponent;
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
    @PostMapping("student/register")
    public ResultVO postRegisterstudent(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_USER);
        userService.addUser(user);
        return ResultVO.ok();
    }
    @PostMapping("teacher/register")
    public ResultVO postRegisterteacher(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_Teacher);
        userService.addUser(user);
        return ResultVO.ok();
    }
}
