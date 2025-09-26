package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.ApplicationFile;
import org.example.springhemingproject02.dox.ApplicationFile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationFileRepository extends ListCrudRepository<ApplicationFile, String>{
}
