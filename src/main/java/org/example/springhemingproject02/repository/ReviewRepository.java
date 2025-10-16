package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.College;
import org.example.springhemingproject02.dox.Review;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends ListCrudRepository<Review, String> {
    Review findByApplicationId(Long applicationId);

    void deleteByApplicationId(Long applicationId);

    @Query("SELECT r.application_id, r.score FROM review r WHERE r.application_id IN (:applicationIds) AND r.status = 'sfdf'")
    List<Object[]> findApprovedScoresByApplicationIds(@Param("applicationIds") List<Long> applicationIds);

    Object findByApplicationId(String applicationId);

    void deleteById(Long id);
}
