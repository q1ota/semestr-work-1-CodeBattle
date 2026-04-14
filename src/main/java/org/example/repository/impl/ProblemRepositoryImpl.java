package org.example.repository.impl;

import org.example.db.DataBaseConnection;
import org.example.entity.ProblemEntity;
import org.example.exception.RepositoryException;
import org.example.mappers.JsonMapper;
import org.example.mappers.ProblemMapper;
import org.example.repository.ProblemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProblemRepositoryImpl implements ProblemRepository {

    @Override
    public long save(ProblemEntity task) {

        String sql = """
        INSERT INTO 
            tasks (title, description, input_example, output_example, examples, tests, difficulty, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getSampleInput());
            preparedStatement.setString(4, task.getSampleOutput());
            preparedStatement.setString(5, JsonMapper.toJson(task.getExamples()));
            preparedStatement.setString(6, JsonMapper.toJson(task.getExamples()));
            preparedStatement.setString(7, task.getDifficulty().name());
            preparedStatement.setTimestamp(8, task.getCreatedAt());

            preparedStatement.executeUpdate();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    task.setId(id);
                    return id;
                } else {
                    throw new RepositoryException("Не удалось получить id задачи");
                }
            } catch (SQLException e) {
                throw new RepositoryException("Не удалось получить сгенерированный id для задачи", e);
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось создать задачу");
        }

    }

    @Override
    public Optional<ProblemEntity> findById(long id) {

        String sql = """
        SELECT * FROM tasks WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ProblemMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти задачу по id = " + id, e);
        }

    }

    @Override
    public List<ProblemEntity> findAll() {

        String sql = """
        SELECT * FROM tasks
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            List<ProblemEntity> list = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    list.add(ProblemMapper.mapRow(resultSet));
            }
            return list;

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось вернуть список задач", e);
        }

    }

    @Override
    public void update(ProblemEntity task) {

        String sql = """
        UPDATE tasks
            SET title, description, input_example, output_example, examples, tests, difficulty, created_at = ?, ?, ?, ?, ?, ?, ?, ?
            WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getSampleInput());
            preparedStatement.setString(4, task.getSampleOutput());
            preparedStatement.setString(5, JsonMapper.toJson(task.getExamples()));
            preparedStatement.setString(6, JsonMapper.toJson(task.getExamples()));
            preparedStatement.setString(7, task.getDifficulty().name());
            preparedStatement.setTimestamp(8, task.getCreatedAt());
            preparedStatement.setLong(9, task.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось обновить данные задачи", e);
        }

    }

    @Override
    public void delete(long id) {

        String sql = """
        DELETE FROM tasks WHERE id = ?
        """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Не удалось удалить задачу", e);
        }

    }
}
