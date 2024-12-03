package com.nicelink.nicer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Integer id;
    private String orig_link;
    private String nice_link;
    private Integer owner_id;

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", orig_link='" + orig_link + '\'' +
                ", nice_link='" + nice_link + '\'' +
                ", owner_id=" + owner_id +
                '}';
    }

    public Object[] getParams() {
        ArrayList<Object> params = new ArrayList<Object>();

        if(nice_link!=null) params.add(nice_link);
        if(orig_link!=null) params.add(orig_link);
        if(owner_id!=null) params.add(owner_id);
        if(id!=null) params.add(id);

        return params.toArray();
    }

    public Object[] getParamsNoId() {
        ArrayList<Object> params = new ArrayList<Object>();

        if(nice_link!=null) params.add(nice_link);
        if(orig_link!=null) params.add(orig_link);
        if(owner_id!=null) params.add(owner_id);

        return params.toArray();
    }

//  rowMapper

    public static Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        Link link = new Link();
        link.setNice_link(rs.getString("nice_link"));
        link.setId(rs.getInt("id"));
        link.setOrig_link(rs.getString("orig_link"));
        link.setOwner_id(rs.getInt("owner_id"));

        return link;
    }

}
