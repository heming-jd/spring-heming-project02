package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.Application;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends ListCrudRepository<Application, String> {
}
