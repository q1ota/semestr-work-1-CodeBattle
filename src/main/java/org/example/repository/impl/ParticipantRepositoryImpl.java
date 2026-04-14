package org.example.repository.impl;

import org.example.db.DataBaseConnection;
import org.example.entity.ParticipantEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;
import org.example.exception.RepositoryException;
import org.example.mappers.ParticipantMapper;
import org.example.repository.ParticipantRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ParticipantRepositoryImpl implements ParticipantRepository {

    @Override
    public ParticipantEntity addParticipant(long sessionId, long userId, ParticipantRole role) {

        String sql = """
            INSERT INTO session_participants (session_id, user_id, role, status, joined_at)
            VALUES (?, ?, ?, ?, now())
            ON CONFLICT (session_id, user_id) DO NOTHING
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setString(3, role.name());
            preparedStatement.setString(4, ParticipantStatus.JOINED.name());
            preparedStatement.executeUpdate();

            return getParticipant(sessionId, userId)
                    .orElseThrow(() -> new RepositoryException("Не удалось получить запись участника после вставки"));
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось добавить пользователя в сессию", e);
        }
    }

    @Override
    public boolean removeParticipant(long sessionId, long userId) {

        String sql = """
            DELETE FROM session_participants
            WHERE session_id = ? AND user_id = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.setLong(2, userId);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось удалить участника сессии", e);
        }
    }

    @Override
    public List<ParticipantEntity> getListOfParticipants(long sessionId) {

        String sql = """
            SELECT * FROM session_participants
            WHERE session_id = ?
            """;

        List<ParticipantEntity> list = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(ParticipantMapper.mapRow(resultSet));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось получить список список участников сессии", e);
        }

    }

    // todo: orElseThrow()?
    @Override
    public Optional<ParticipantEntity> getParticipant(long sessionId, long userId) {

        String sql = """
            SELECT * FROM session_participants
            WHERE session_id = ? AND user_id = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.setLong(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ParticipantMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти участника сессии", e);
        }
    }

    @Override
    public boolean updateStatus(long sessionId, long userId, ParticipantStatus status) {

        String sql = """
            UPDATE session_participants
            SET status = ?
            WHERE session_id = ? AND user_id = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, sessionId);
            preparedStatement.setLong(3, userId);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось обновить статус участника", e);
        }

    }

    @Override
    public boolean isParticipantSubmitted(long sessionId, long userId) {

        String sql = """
            SELECT status 
            FROM session_participants 
            WHERE session_id = ? 
            AND user_id = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, sessionId);
            ps.setLong(2, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    if (status == null)
                        return false;
                    return ParticipantStatus.valueOf(status) == ParticipantStatus.SUBMITTED;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось проверить статус участника", e);
        }
    }

    @Override
    public int countNotSubmitted(long sessionId) {

        String sql = """
            SELECT COUNT(*) AS count
            FROM session_participants
            WHERE session_id = ? AND status != ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.setString(2, ParticipantStatus.SUBMITTED.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось посчитать количество не присоединившихся участников", e);
        }

    }

    @Override
    public int countParticipants(long sessionId) {

        String sql = """
            SELECT COUNT(*) AS count
            FROM session_participants
            WHERE session_id = ? AND role = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, sessionId);
            preparedStatement.setString(2, ParticipantRole.PARTICIPANT.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось посчитать количество участников сессии", e);
        }

    }
}
