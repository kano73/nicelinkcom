package com.nicelink.nicer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String level;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public Object[] getParams() {
        List<Object> params = new ArrayList<Object>();

        if(this.username!=null) params.add(this.username);
        if(this.password!=null) params.add(this.password);
        if(this.email!=null) params.add(this.email);
        if(this.role!=null) params.add(this.role);
        if(this.level!=null) params.add(this.level);
        if(this.id!=null) params.add(this.id);

        return params.toArray();
    }

    public Object[] getALLParamsNoId() {
        List<Object> params = new ArrayList<Object>();

        if(this.username!=null) params.add(this.username);
        if(this.password!=null) params.add(this.password);
        if(this.email!=null) params.add(this.email);
        if(this.role!=null) params.add(this.role);
        if(this.level!=null) params.add(this.level);

        return params.toArray();
    }

//    row mapper
    public static User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setLevel(rs.getString("level"));
        return user;
    }
}
