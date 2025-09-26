package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, String> {
}
