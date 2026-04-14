package org.example.mappers;

import org.example.entity.ParticipantEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantMapper {

    private ParticipantMapper() {}

    public static ParticipantEntity mapRow(ResultSet resultSet) throws SQLException {

        return ParticipantEntity.builder()
                .sessionId(resultSet.getLong("session_id"))
                .userId(resultSet.getLong("user_id"))
                .role(ParticipantRole.valueOf(resultSet.getString("role")))
                .status(ParticipantStatus.valueOf(resultSet.getString("status")))
                .joinedAt(resultSet.getTimestamp("joined_at"))
                .build();

    }
}