package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.ApplicationFile;
import org.example.springhemingproject02.dox.ApplicationFile;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplicationFileRepository extends ListCrudRepository<ApplicationFile, String>{
    @Transactional
    @Modifying
    @Query(value = "delete from application_file where application_id = :id")
    void deletejoin(String id);

    List<ApplicationFile> findByApplicationId(String id);


}
