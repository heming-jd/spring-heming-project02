package org.example.springhemingproject02.mapper;

import org.example.springhemingproject02.dto.StudentBig;
import org.example.springhemingproject02.dox.StudentScore;
import org.example.springhemingproject02.dox.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class StudentBigRowMapper implements RowMapper<StudentBig> {
    @Override
    public StudentBig mapRow(ResultSet rs, int rowNum) throws SQLException {
        // 创建 User 对象
        User user = User.builder()
                .id(rs.getString("u_id"))
                .account(rs.getString("account"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .phone(rs.getString("phone"))
                .email(rs.getString("email"))
                .collegeId(rs.getString("college_id"))
                .role(rs.getString("role"))
                .studentNumber(rs.getString("student_number"))
                .teacherNumber(rs.getString("teacher_number"))
                .categoryId(rs.getString("category_id"))
                .majorId(rs.getString("major_id"))
                .createdTime(rs.getObject("created_time", LocalDateTime.class))
                .updatedTime(rs.getObject("updated_time", LocalDateTime.class))
                .build();

        // 创建 StudentScore 对象
        StudentScore studentScore = StudentScore.builder()
                .id(rs.getString("s_id"))
                .studentId(rs.getString("student_id"))
                .weightedScore(rs.getBigDecimal("weighted_score"))
                .majorRank(rs.getInt("major_rank"))
                .createTime(rs.getObject("create_time", LocalDateTime.class))
                .updateTime(rs.getObject("update_time", LocalDateTime.class))
                .build();

        // 创建 StudentBig 对象
        return StudentBig.builder()
                .user(user)
                .studentScore(studentScore)
                .commit(rs.getInt("commit_count"))
                .success(rs.getInt("success_count"))
                .build();
    }
}