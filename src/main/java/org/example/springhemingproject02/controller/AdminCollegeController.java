package org.example.springhemingproject02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admincollege/")
@RequiredArgsConstructor
public class AdminCollegeController {
    @PostMapping("addnode")
    public void addNode() {
        System.out.println("addnode");
    }
}
