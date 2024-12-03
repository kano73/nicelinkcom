package com.nicelink.nicer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionOnLinkOnUser {
    private String username;
    private String nice_link;
    private String orig_link;
    private ZonedDateTime time_stamp;
    private String ip_of_user;

//    row mapper
    public static ActionOnLinkOnUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActionOnLinkOnUser aolou = new ActionOnLinkOnUser();

        String timestampString = rs.getString("time_stamp");

        aolou.setTime_stamp(ZonedDateTime.parse(timestampString, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        aolou.setIp_of_user(rs.getString("ip_of_user"));
        aolou.setOrig_link(rs.getString("orig_link"));
        aolou.setNice_link(rs.getString("nice_link"));
        aolou.setUsername(rs.getString("username"));
        return aolou;
    }
}
