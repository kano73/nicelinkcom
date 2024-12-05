package com.nicelink.nicer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private String time_stamp;
    private LocalDate date_stamp;
    private Integer link_id;
    private String ip_of_user;

    public Action(Integer link_id, String ip_of_user) {
        this.link_id = link_id;
        this.ip_of_user = ip_of_user;
    }

    @Override
    public String toString() {
        return "Action{" +
                "time_stamp=" + time_stamp +
                ", link_id=" + link_id +
                ", ip_of_user='" + ip_of_user + '\'' +
                '}';
    }

    public Object[] getParamsNoTimeAndDate(){
        return new Object[]{link_id, ip_of_user};
    }

//    row mapper

    public static Action mapRow(ResultSet rs, int rowNum) throws SQLException {
        Action action = new Action();

        String timestampString = rs.getString("time_stamp");
        String date_stampString = rs.getString("date_stamp");

        action.setDate_stamp(LocalDate.parse(date_stampString));
        action.setTime_stamp(timestampString);
        action.setIp_of_user(rs.getString("ip_of_user"));
        action.setLink_id(rs.getInt("link_id"));
        return action;
    }
}
