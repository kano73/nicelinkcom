package com.nicelink.nicer.repository;

import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ActionRepository {
    private final JdbcTemplate jdbcTemplate;

    public ActionRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

//    select

    public List<Action> getAllActionsOnLinkById(Integer linkId){
        String sql = "SELECT time_stamp, ip_of_user FROM my_action WHERE link_id = ?";

        return jdbcTemplate.query(sql, new Object[]{linkId}, Action::mapRow);
    }

    public List<ActionOnLinkOnUser> getAllActionInfoOnLinkByNiceLink(String niceLink){
        String sql = "SELECT u.username, l.orig_link, l.nice_link, a.time_stamp, a.ip_of_user FROM my_action a JOIN my_link l ON a.link_id=l.id JOIN my_user u ON l.owner_id = u.id WHERE l.nice_link = ?";

        return jdbcTemplate.query(sql, new Object[]{niceLink},ActionOnLinkOnUser::mapRow);
    }

    public List<Action> getAllActionsLinkByNiceLink(String niceLink){
        String sql = "SELECT a.time_stamp, a.ip_of_user FROM my_action a JOIN my_link l ON a.link_id=l.id WHERE l.nice_link = ?";

        return jdbcTemplate.query(sql, new Object[]{niceLink},Action::mapRow);
    }

//    insert

    public boolean addAction(Action action){
        String sql = "INSERT INTO my_action (time_stamp, link_id, ip_of_user) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,action.getParams()) > 0;

    }
}
