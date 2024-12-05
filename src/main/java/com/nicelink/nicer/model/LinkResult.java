package com.nicelink.nicer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkResult {
    private Integer id;
    private String orig_link;
    private String nice_link;
    private Integer action_num;

    public static LinkResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        LinkResult link = new LinkResult();
        link.setNice_link(rs.getString("nice_link"));
        link.setId(rs.getInt("id"));
        link.setOrig_link(rs.getString("orig_link"));
        link.setAction_num(rs.getInt("actions_num"));

        return link;
    }
}
