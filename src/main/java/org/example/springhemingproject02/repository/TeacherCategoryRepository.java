package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.TeacherCategory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCategoryRepository extends ListCrudRepository<TeacherCategory, String> {
}
