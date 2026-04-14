package org.example.service;

import org.example.entity.ProblemEntity;

import java.util.List;
import java.util.Optional;

public interface ProblemService {
    long save(ProblemEntity task);
    List<ProblemEntity> findAll();
    Optional<ProblemEntity> findById(long id);
    void update(ProblemEntity task);
    void delete(long id);
}
