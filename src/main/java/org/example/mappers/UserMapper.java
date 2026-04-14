package org.example.mappers;

import org.example.entity.UserEntity;
import org.example.entity.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    private UserMapper() {}

    public static UserEntity mapRow(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .passwordHash(resultSet.getString("password_hash"))
                .role(UserRole.valueOf(resultSet.getString("role")))
                .build();
    }
}
