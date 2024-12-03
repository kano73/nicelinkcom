package com.nicelink.nicer.model;

import com.nicelink.nicer.repository.LinkRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
public class Action {
    private ZonedDateTime time_stamp;
    private Integer link_id;
    private String ip_of_user;

    public Action(ZonedDateTime time_stamp, Integer link_id, String ip_of_user) {
        this.time_stamp = time_stamp;
        this.link_id = link_id;
        this.ip_of_user = ip_of_user;
    }

    public void setLink_idByNiceLink(String nice_link) {
        LinkRepository repository = new LinkRepository(new JdbcTemplate());

        Link link = new Link();
        link.setNice_link(nice_link);

        this.link_id = repository.getLinksByParams(link).stream().findFirst().orElse(null).getId();
    }

    @Override
    public String toString() {
        return "Action{" +
                "time_stamp=" + time_stamp +
                ", link_id=" + link_id +
                ", ip_of_user='" + ip_of_user + '\'' +
                '}';
    }

    public Object[] getParams(){
        return new Object[]{Timestamp.from(time_stamp.toInstant()), link_id, ip_of_user};
    }

//    row mapper

    public static Action mapRow(ResultSet rs, int rowNum) throws SQLException {
        Action action = new Action();
        String timestampString = rs.getString("time_stamp");

        action.setTime_stamp(ZonedDateTime.parse(timestampString, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        action.setIp_of_user(rs.getString("ip_of_user"));
        action.setLink_id(rs.getInt("link_id"));
        return action;
    }
}
