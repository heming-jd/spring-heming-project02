package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.NodeClosure;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NodeClosureRepository extends ListCrudRepository<NodeClosure, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO node_closure (ancestor_id, descendant_id, level, create_time) " +
            "SELECT ancestor_id, :descendantId, level + 1, NOW() " +
            "FROM node_closure " +
            "WHERE descendant_id = :parentId " +
            "UNION ALL " +
            "SELECT :descendantId, :descendantId, 0, NOW()")
    void addNodeClosure(Long parentId, Long descendantId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM node_closure WHERE ancestor_id = :id OR descendant_id = :id")
    void deleteNodeRelations(@Param("id") Long id);

    @Query("SELECT nc.ancestor_id FROM node_closure nc " +
            "JOIN node n ON nc.ancestor_id = n.id " +
            "WHERE nc.descendant_id = :descendantId AND n.max_score > 0 " +
            "ORDER BY nc.level ASC LIMIT 1")
    String findNearestMaxScoreAncestor(@Param("descendantId") Long descendantId);

    @Query("SELECT descendant_id FROM node_closure WHERE ancestor_id = :ancestorId")
    List<Long> findDescendantIdsByAncestorId(@Param("ancestorId") Long ancestorId);
}
