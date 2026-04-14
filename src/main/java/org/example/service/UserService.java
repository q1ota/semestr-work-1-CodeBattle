package org.example.service;

import org.example.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> authenticate(String username, String password);
    long register(String username, String password);
    Optional<UserEntity> findById(long userId);
}
