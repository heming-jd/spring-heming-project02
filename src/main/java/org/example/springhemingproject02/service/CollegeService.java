package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.Category;
import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.Major;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.repository.CategoryRepository;
import org.example.springhemingproject02.repository.CollegeRepository;
import org.example.springhemingproject02.repository.MajorRepository;
import org.example.springhemingproject02.repository.UserRepository;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MajorRepository majorRepository;
    @Transactional
    public ResultVO addcollege(College college){
        collegeRepository.save(college);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO deletecollege(Long id){
        collegeRepository.deleteById(id);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO updatecollege(College college){
        collegeRepository.save(college);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO addcollegeadmin(User user){
        userRepository.save(user);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO deletecollegeadmin(Long id){
        userRepository.deleteById(id);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO addcategory(Category category){
        categoryRepository.save(category);
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO getAllcollege() {
        return ResultVO.builder()
                .code(200)
                .data(collegeRepository.findAll())
                .build();
    }

    public ResultVO deletecategory(String id) {
        categoryRepository.deleteById(id);
        return ResultVO.ok();
    }

    public ResultVO getCategories(Long collegeId) {
        List<Category> categories = categoryRepository.findBycollegeId(collegeId);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("categories", categories))
                .build();
    }

    public ResultVO getmajors(Long categoryId) {
        List<Major> majors = majorRepository.findAllBycategoryId(categoryId);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("majors", majors))
                .build();
    }

    public ResultVO addmajor(Major major) {
        majorRepository.save(major);
        return ResultVO.ok();
    }

    public ResultVO deletemajor(Long id) {
        majorRepository.deleteById(id);
        return ResultVO.ok();
    }

    public ResultVO updatecategory(Category category) {
        categoryRepository.save(category);
        return ResultVO.ok();
    }

    public ResultVO updatemajor(Major major) {
        majorRepository.save(major);
        return ResultVO.ok();
    }

    public ResultVO getcollege() {
        List<College> colleges = collegeRepository.findAll();
        return ResultVO.builder()
                .code(200)
                .data(Map.of("colleges", colleges))
                .build();
    }

    public ResultVO getcollegeadmin() {
        List<User> users = userRepository.findByRole(User.ROLE_COLLEGEAdmin);
        return ResultVO.builder()
                .code(200)
                .data(Map.of("users", users))
                .build();
    }
}
