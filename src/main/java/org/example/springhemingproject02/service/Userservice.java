package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.dto.StudentBig;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.exception.XException;
import org.example.springhemingproject02.repository.UserRepository;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class Userservice {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void resetpassword(String account){
        User user = userRepository.findByAccount(account);
        if(user == null){
            throw XException.builder()
                    .codeN(Code.ERROR)
                    .message("用户不存在")
                    .build();
        }
        user.setPassword(passwordEncoder.encode(account));
    }
    @Transactional
    public User getUser(String account){
        return userRepository.findByAccount(account);
    }
    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public User getUserbyId(String uid) {
        return userRepository.findById(uid).orElseThrow(() -> XException.builder()
                .codeN(Code.ERROR)
                .message("用户不存在")
                .build());
    }
    @Transactional
    public ResultVO getStudentsByTeacher(String collegeId) {
        List<User> students = userRepository.findStudentByCollegeId(collegeId);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("students",students))
                .build();
    }
    @Transactional
    public ResultVO getStudentsBycategory(String categoryId) {
        List<StudentBig> students = userRepository.findStudentByCategoryId(categoryId);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("students",students))
                .build();
    }
}
