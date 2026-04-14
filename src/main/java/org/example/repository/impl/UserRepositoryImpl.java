package org.example.repository.impl;

import org.example.db.DataBaseConnection;
import org.example.entity.UserEntity;
import org.example.exception.RepositoryException;
import org.example.repository.UserRepository;
import org.example.mappers.UserMapper;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public long save(UserEntity user) {

        String sql = """
            INSERT INTO
                users (username, password_hash, role) 
                VALUES (?, ?, ?) 
                RETURNING id
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getRole().name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                } else {
                    throw new RepositoryException("Не удалось получить id");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при сохранении пользователя", e);
        }
    }


    @Override
    public Optional<UserEntity> findByUsername(String username) {

        String sql = """
            SELECT * FROM users WHERE username = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при поиске пользователя по username", e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {

        String sql = """
            SELECT * FROM users WHERE id = ?
            """;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserMapper.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при поиске пользователя по id", e);
        }
    }

}
