package org.example.service.impl;

import org.example.entity.UserEntity;
import org.example.entity.enums.UserRole;
import org.example.exception.ServiceException;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.utils.PasswordUtil;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public Optional<UserEntity> authenticate(String username, String password) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty())
            return Optional.empty();

        UserEntity user = userOpt.get();
        if (PasswordUtil.verify(password, user.getPasswordHash())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public long register(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new ServiceException("Пользователь с таким именем уже существует");
        }

        String hash = PasswordUtil.hash(password);
        UserEntity newUser = UserEntity.builder()
                .username(username)
                .passwordHash(hash)
                .role(UserRole.valueOf("USER"))
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public Optional<UserEntity> findById(long userId) {
        return userRepository.findById(userId);
    }
}
