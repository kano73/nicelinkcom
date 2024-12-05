package com.nicelink.nicer.repository;

import com.nicelink.nicer.exeptions.user.InvalidUserException;
import com.nicelink.nicer.exeptions.user.UserAlreadyExistsExeption;
import com.nicelink.nicer.exeptions.user.UserNotFoundException;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

//    SELECT

    public User getUserByUsername(@NonNull String username) throws UserNotFoundException {
        User user = new User();
        user.setUsername(username);
        String sql = sqlSelectBuilder(user);

        User userFound =  jdbcTemplate.query(sql, user.getParams(),User::mapRow).stream().findFirst().orElse(null);

        if(userFound == null){
            throw new UserNotFoundException("no user exists with username: "+username);
        }
        return userFound;
    }

    public List<Link> getAllLinksForUser(String username)  {
        String sql = "SELECT id, orig_link, nice_link, owner_id FROM my_link l JOIN my_user u ON l.owner_id = u.id WHERE username = ?";

        return jdbcTemplate.query(sql,new Object[]{username},(rs,rwNum)->{
            Link link = new Link();
            link.setId(rs.getInt("id"));
            link.setNice_link(rs.getString("nice_link"));
            link.setOrig_link(rs.getString("orig_link"));
            link.setOwner_id(rs.getInt("owner_id"));

            return link;
        });
    }

    private String sqlSelectBuilder(User user){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM my_user WHERE");

        if(user.getUsername()!=null)sqlBuilder.append(" username = ? AND");
        if(user.getEmail()!=null)sqlBuilder.append(" email = ? AND");
        if(user.getId()!=null)sqlBuilder.append(" id = ? AND");

        sqlBuilder.delete(sqlBuilder.length()-3,sqlBuilder.length());

        log.info("select builder sql:"+sqlBuilder);

        return sqlBuilder.toString();
    }

//    UPDATE

    public boolean updateUserInfoById(User user) throws InvalidUserException {
        if(user.getId()==null){
            throw new InvalidUserException("authenticated user missing id: try to relogin into system.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String sql = sqlUpdateBuilder(user);

        try {
            return jdbcTemplate.update(sql, user.getParams()) > 0;
        } catch (DataAccessException e) {
            throw new RuntimeException("error updating user info", e);
        }
    }

    private String sqlUpdateBuilder(User user){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE my_user SET");
        if(user.getUsername()!=null)sqlBuilder.append(" username = ?,");
        if(user.getPassword()!=null)sqlBuilder.append(" password = ?,");
        if(user.getEmail()!=null)sqlBuilder.append(" email = ?,");
        if(user.getRole()!=null)sqlBuilder.append(" role = ?,");
        if(user.getLevel()!=null)sqlBuilder.append(" level = ?,");

        sqlBuilder.deleteCharAt(sqlBuilder.length()-1);

        sqlBuilder.append(" WHERE id = ?");

        log.info("update builder sql:"+sqlBuilder);

        return sqlBuilder.toString();
    }

//    INSERT

    public boolean postUser(User user) throws UserAlreadyExistsExeption {

        log.info("method postUser started");

        try{
            if (this.getUserByUsername(user.getUsername()) != null) {
                log.info("user already exists");
                throw new UserAlreadyExistsExeption("User with username: " + user.getUsername() + " already exists");
            }
        }catch (UserNotFoundException e){
            log.info("good: user does not exist with username: "+user.getUsername() );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("user details:"+user);

        String sql = "INSERT INTO my_user(username, password, email, role, level) VALUES (?,?, ?, ?,?)";

        return jdbcTemplate.update(sql,user.getALLParamsNoId()) > 0;

    }

//    DELETE

    public boolean deleteUserByUsername(String username) throws UserNotFoundException, RuntimeException{

        String sql = "DELETE FROM my_user WHERE username = ?";

        boolean isDeleted = jdbcTemplate.update(sql, new Object[]{username})>0;

        if(!isDeleted){
            throw new RuntimeException("not deleted");
        }

        return true;
    }
}
