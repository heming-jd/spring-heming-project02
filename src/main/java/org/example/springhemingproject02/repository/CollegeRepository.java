package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.College;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends ListCrudRepository<College, String> {

    void deleteById(Long id);
}
