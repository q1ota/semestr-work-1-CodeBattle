package org.example.mappers;

import org.example.entity.SessionEntity;
import org.example.entity.enums.SessionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class SessionMapper {

    private SessionMapper() {}

    public static SessionEntity mapRow(ResultSet resultSet) throws SQLException {

        Integer duration = resultSet.getInt("duration_seconds");
        if (resultSet.wasNull())
            duration = null;

        return SessionEntity.builder()
                .id(resultSet.getLong("id"))
                .ownerId(resultSet.getLong("owner_id"))
                .inviteToken(resultSet.getString("invite_token"))
                .status(SessionStatus.valueOf(resultSet.getString("status")))
                .startTime(resultSet.getTimestamp("start_time"))
                .durationSeconds(duration)
                .createdAt(resultSet.getTimestamp("created_at"))
                .taskId(resultSet.getLong("task_id"))
                .build();

    }
}