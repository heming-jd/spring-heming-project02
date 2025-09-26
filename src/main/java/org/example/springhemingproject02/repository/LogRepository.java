package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.Log;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends ListCrudRepository<Log, String> {
}
