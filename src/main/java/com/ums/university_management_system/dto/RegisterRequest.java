package com.ums.university_management_system.dto;

import com.ums.university_management_system.model.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;  // Change from userName to username
    private String email;
    private String password;
    private Role role;
}
