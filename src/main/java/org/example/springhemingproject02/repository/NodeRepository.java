package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.Node;
import org.example.springhemingproject02.dto.NodeBig;
import org.example.springhemingproject02.mapper.NodesRowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface NodeRepository extends ListCrudRepository<Node, String> {
    @Transactional
    @Query(value = "select n.node_name,n.description,n.id,n.category_id,n.limit_count,n.max_score,nc.level \n" +
            "from node n\n" +
            "inner join node_closure nc on n.id = nc.descendant_id\n" +
            "where nc.ancestor_id = :id \n" +
            "  and nc.level > 0\n" +
            "order by nc.level asc",
            rowMapperClass = NodesRowMapper.class)
    List<NodeBig> findNodeBigsById(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM node WHERE id = :id")
    void deleteNode(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT n.* FROM node n WHERE n.parent_id IS NULL AND n.category_id = :categoryId")
    List<Node> findFirstNodes(@Param("categoryId") Long categoryId);

    @Query("SELECT * FROM node n WHERE n.id IN (" +
            "SELECT nc.ancestor_id FROM node_closure nc WHERE nc.descendant_id = :leafNodeId) " +
            "AND n.limit_count > 0")
    List<Node> findAncestorNodesWithLimit(@Param("leafNodeId") Long leafNodeId);


    @Query("SELECT * FROM node n " +
            "JOIN node_closure nc ON n.id = nc.ancestor_id " +
            "WHERE nc.descendant_id = :id " +
            "ORDER BY nc.level ASC")
    List<Node> findAncestorNodes(Long id);

    Node findById(Long leafNodeId);
}
