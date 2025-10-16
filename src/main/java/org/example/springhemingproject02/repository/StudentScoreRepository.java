package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.StudentScore;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentScoreRepository extends ListCrudRepository<StudentScore, String> {
    Object findBystudentId(Long uid);
}
