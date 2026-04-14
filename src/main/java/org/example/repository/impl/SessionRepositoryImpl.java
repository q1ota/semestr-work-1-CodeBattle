package org.example.repository.impl;

import org.example.db.DataBaseConnection;
import org.example.entity.SessionEntity;
import org.example.entity.enums.SessionStatus;
import org.example.exception.RepositoryException;
import org.example.mappers.SessionMapper;
import org.example.repository.SessionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SessionRepositoryImpl implements SessionRepository {

    @Override
    public long save(SessionEntity session) {

        String sql = """
        INSERT INTO
            sessions (task_id, owner_id, invite_token, status, start_time, duration_seconds, created_at)
            VALUES (?, ?, ?, ?, ?, ?, now())
            RETURNING id, created_at
        """;

        if (session.getInviteToken() == null || session.getInviteToken().isBlank()) {
            session.setInviteToken(java.util.UUID.randomUUID().toString());
        }
        if (session.getStatus() == null) {
            session.setStatus(SessionStatus.LOBBY);
        }

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, session.getTaskId());
            preparedStatement.setLong(2, session.getOwnerId());
            preparedStatement.setString(3, session.getInviteToken());
            preparedStatement.setString(4, session.getStatus().name());
            preparedStatement.setNull(5, Types.TIMESTAMP);

            if (session.getDurationSeconds() != null)
                preparedStatement.setInt(6, session.getDurationSeconds());
            else
                preparedStatement.setNull(6, Types.INTEGER);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    session.setId(id);
                    session.setCreatedAt(resultSet.getTimestamp("created_at"));
                    return id;
                } else {
                    throw new RepositoryException("Не удалось получить id/created_at");
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось создать сессию", e);
        }
    }

    @Override
    public Optional<SessionEntity> findById(long sessionId) {

        String sql = """
        SELECT * FROM sessions
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(SessionMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти сессию по id = " + sessionId, e);
        }
    }

    @Override
    public Optional<SessionEntity> findByInviteToken(String token) {

        String sql = """
        SELECT * FROM sessions
            WHERE invite_token = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, token);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(SessionMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти сессию по токену = " + token, e);
        }
    }

    @Override
    public List<SessionEntity> listActive() {

        String sql = """
        SELECT * FROM sessions
            WHERE status = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, SessionStatus.RUNNING.name());
            List<SessionEntity> list = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    list.add(SessionMapper.mapRow(resultSet));
            }
            return list;

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось вернуть список активных сессий", e);
        }
    }

    @Override
    public List<SessionEntity> listByOwner(long ownerId) {

        String sql = """
        SELECT * FROM sessions
            WHERE owner_id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, ownerId);
            List<SessionEntity> list = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    list.add(SessionMapper.mapRow(resultSet));
            }
            return list;

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось вернуть список созданных сессий" ,e);
        }
    }

    @Override
    public void updateStatus(long sessionId, SessionStatus status) {

        String sql = """    
        UPDATE sessions
            SET status = ?
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, sessionId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось обновить данные", e);
        }
    }

    @Override
    public void updateStartTime(long sessionId, Timestamp startTime) {

        String sql = """
        UPDATE sessions
            SET start_time = ?
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setTimestamp(1, startTime);
            preparedStatement.setLong(2, sessionId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось обновить начальное время сессии", e);
        }
    }

    @Override
    public boolean isExistsById(long sessionId) {

        String sql = """
        SELECT * FROM sessions
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось проверить существование сессии по id = " + sessionId, e);
        }
    }

    @Override
    public boolean isExistByToken(String token) {

        String sql = """
        SELECT * FROM sessions
            WHERE invite_token = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, token);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось проверить существование сессии по токену = " + token, e);
        }
    }

    @Override
    public void delete(long sessionId) {

        String sql = """
        DELETE FROM sessions
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось удалить сессию", e);
        }
    }
}