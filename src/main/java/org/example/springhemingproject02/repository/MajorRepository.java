package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.Major;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends ListCrudRepository<Major, String> {
    List<Major> findAllById(String categoryId);
}
