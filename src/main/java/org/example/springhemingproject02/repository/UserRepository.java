package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.dto.StudentBig;
import org.example.springhemingproject02.mapper.NodesRowMapper;
import org.example.springhemingproject02.mapper.StudentBigRowMapper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);
    @Transactional
    @Query(value = "select * from user where college_id = :collegeId and role = 'hOl7U' ")
    List<User> findStudentByCollegeId(String collegeId);
    @Transactional
    @Query(value = """
        SELECT 
            u.id as u_id, u.account, u.password, u.name, u.phone, u.email, 
            u.college_id, u.role, u.student_number, u.teacher_number, 
            u.category_id, u.major_id, u.created_time, u.updated_time,
            s.id as s_id, s.student_id, s.weighted_score, s.major_rank, 
            s.create_time, s.update_time,
            (SELECT COUNT(*) FROM application a1 WHERE a1.student_id = u.id AND a1.status = 'wedwa') as commit_count,
            (SELECT COUNT(*) FROM application a2 WHERE a2.student_id = u.id AND a2.status = 'sfdf') as success_count
        FROM user u
        JOIN student_score s ON u.id = s.student_id
        WHERE u.category_id = :categoryId
    """, rowMapperClass = StudentBigRowMapper.class)
    List<StudentBig> findStudentByCategoryId(String categoryId);

    List<User> findByRole(String roleCollegeAdmin);
}
