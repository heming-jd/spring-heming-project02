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
    @PostMapping("addcollege")
    public ResultVO addcollege(@RequestBody College college){
        collegeService.addcollege(college);
        return  ResultVO.ok();
    }
    @PostMapping("deletecollege/{id}")
    public ResultVO deletecollege(@PathVariable String id){
        collegeService.deletecollege(id);
        return  ResultVO.ok();
    }
    @PostMapping("updatecollege")
    public ResultVO updatecollege(@RequestBody College college){
        collegeService.updatecollege(college);
        return  ResultVO.ok();
    }
    @PostMapping("addcollegeadmin")
    public ResultVO addcollegeadmin(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.ROLE_COLLEGEAdmin);
        collegeService.addcollegeadmin(user);
        return  ResultVO.ok();
    }
    @PostMapping("deletecollegeadmin/{id}")
    public ResultVO deletecollegeadmin(@PathVariable String id){
        collegeService.deletecollegeadmin(id);
        return  ResultVO.ok();
    }
    @PostMapping("resetcollegeadminpassword/{account}")
    public ResultVO resetcollegeadminpassword(@PathVariable String account){
        userservice.resetpassword(account);
        return  ResultVO.ok();
    }
}
