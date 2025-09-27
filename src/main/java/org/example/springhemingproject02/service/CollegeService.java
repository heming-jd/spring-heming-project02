package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.repository.CollegeRepository;
import org.example.springhemingproject02.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addcollege(College college){
        collegeRepository.save(college);
    }
    @Transactional
    public void deletecollege(String id){
        collegeRepository.deleteById(id);
    }
    @Transactional
    public void updatecollege(College college){
        collegeRepository.save(college);
    }
    @Transactional
    public void addcollegeadmin(User user){
        userRepository.save(user);
    }
    @Transactional
    public void deletecollegeadmin(String id){
        userRepository.deleteById(id);
    }
}
