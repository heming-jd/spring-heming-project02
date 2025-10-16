package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.Application;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface ApplicationRepository extends ListCrudRepository<Application, String> {
    List<Application> findByStudentId(Long id);

    @Query(value = "SELECT COUNT(*) FROM application WHERE student_id = :id AND status = 'wedwa'")
    Integer countStatus1ByStudentId(Long id);
    @Query(value = "SELECT COUNT(*) FROM application WHERE student_id = :id AND status = 'sfdf'")
    Integer countStatus2ByStudentId(Long id);

    @Query("SELECT COUNT(*) FROM application a " +
            "WHERE a.student_id = :studentId " +
            "AND a.leaf_node_id IN (" +
            "    SELECT nc.descendant_id FROM node_closure nc " +
            "    WHERE nc.ancestor_id = :ancestorId" +
            ")")
    long countByStudentAndNodeAncestor(@Param("studentId") Long studentId, @Param("ancestorId") Long ancestorId);

    @Query("SELECT * FROM application a " +
            "JOIN node n ON a.leaf_node_id = n.id " +
            "JOIN category c ON n.category_id = c.id " +
            "WHERE a.student_id = :studentId AND c.id = :categoryId AND a.status = 'sfdf'")
    List<Application> findByStudentIdAndCategory(@Param("studentId") Long studentId,
                                                 @Param("categoryId") Long categoryId);

    @Query("SELECT a.* FROM application a WHERE a.student_id = :studentId AND a.leaf_node_id IN (:leafNodeIds)")
    List<Application> findByStudentIdAndLeafNodeIds(@Param("studentId") Long studentId,
                                                    @Param("leafNodeIds") List<Long> leafNodeIds);

    Application findById(Long id);

    void deleteById(Long id);
}
