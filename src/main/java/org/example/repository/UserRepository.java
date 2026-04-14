package org.example.repository;


import org.example.entity.UserEntity;
import org.example.exception.RepositoryException;

import java.util.Optional;

public interface UserRepository {
    long save(UserEntity user) throws RepositoryException;
    Optional<UserEntity> findByUsername(String username) throws RepositoryException;
    Optional<UserEntity> findById(Long id) throws RepositoryException;
}
