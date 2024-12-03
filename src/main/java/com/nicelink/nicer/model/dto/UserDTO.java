package com.nicelink.nicer.model.dto;

import com.nicelink.nicer.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @Size(min = 3, max = 200)
    private String username;

    @Size(min = 4, max = 200)
    private String password;

    @Email
    private String email;

    private String role;

    private String level;

    public User convertToUser() {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(Objects.requireNonNullElse(this.role, "USER"));
        user.setLevel(Objects.requireNonNullElse(this.level, "1"));

        return user;
    }
}
