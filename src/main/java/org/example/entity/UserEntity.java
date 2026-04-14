package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.UserRole;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    private Long id;
    private String username;
    private String passwordHash;
    private UserRole role;
}