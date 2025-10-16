package org.example.springhemingproject02.controller;

import lombok.RequiredArgsConstructor;
import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.service.CollegeService;
import org.example.springhemingproject02.service.Userservice;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final CollegeService collegeService;
    private final Userservice userservice;
    private final PasswordEncoder encoder;
    //增删改查学院 学院管理员
    @GetMapping("college")
    public ResultVO getcollege(){
        return collegeService.getcollege();
    }
    @PostMapping("college")
    public ResultVO addcollege(@RequestBody College college){
        collegeService.addcollege(college);
        return  ResultVO.ok();
    }
    @DeleteMapping("college/{id}")
    public ResultVO deletecollege(@PathVariable Long id){
        collegeService.deletecollege(id);
        return  ResultVO.ok();
    }
    @PutMapping("college")
    public ResultVO updatecollege(@RequestBody College college){
        collegeService.updatecollege(college);
        return  ResultVO.ok();
    }
    @GetMapping("collegeadmin")
    public ResultVO getcollegeadmin(){
        return collegeService.getcollegeadmin();
    }
    @PostMapping("collegeadmin")
    public ResultVO addcollegeadmin(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_COLLEGEAdmin);
        collegeService.addcollegeadmin(user);
        return  ResultVO.ok();
    }
    @DeleteMapping("collegeadmin/{id}")
    public ResultVO deletecollegeadmin(@PathVariable Long id){
        collegeService.deletecollegeadmin(id);
        return  ResultVO.ok();
    }
    @PutMapping("collegeadminpasswordreset/{account}")
    public ResultVO resetcollegeadminpassword(@PathVariable String account){
        userservice.resetpassword(account);
        return  ResultVO.ok();
    }
}
