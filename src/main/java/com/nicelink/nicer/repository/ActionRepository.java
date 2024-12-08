package com.nicelink.nicer.repository;

import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActionRepository {
    private final JdbcTemplate jdbcTemplate;

    public ActionRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

//    select

    public List<Action> getAllActionsOnLinkById(Integer linkId){
        String sql = "SELECT time_stamp, ip_of_user, date_stamp, link_id FROM my_action WHERE link_id = ?";

        return jdbcTemplate.query(sql, new Object[]{linkId}, Action::mapRow);
    }

//    insert

    public boolean addAction(Action action){
        String sql = "INSERT INTO my_action (link_id, ip_of_user) VALUES ( ?, ?)";

        return jdbcTemplate.update(sql,action.getParamsNoTimeAndDate()) > 0;

    }
}
