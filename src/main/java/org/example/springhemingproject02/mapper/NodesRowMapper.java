package org.example.springhemingproject02.mapper;


import org.example.springhemingproject02.dto.NodeBig;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NodesRowMapper implements RowMapper<NodeBig> {
    @Override
    public NodeBig mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NodeBig.builder()
                .nodeName(rs.getString("n.node_name"))
                .categoryId(rs.getString("category_id"))
                .description(rs.getString("n.description"))
                .id(rs.getString("n.id"))
                .level(rs.getInt("level"))
                .limitCount(rs.getInt("limit_count"))
                .maxScore(rs.getBigDecimal("max_score"))
                .build();
    }
}
