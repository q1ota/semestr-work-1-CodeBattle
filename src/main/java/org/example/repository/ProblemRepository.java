package org.example.repository;

import org.example.entity.ProblemEntity;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    long save(ProblemEntity task);
    Optional<ProblemEntity> findById(long id);
    List<ProblemEntity> findAll();
    void update(ProblemEntity task);
    void delete(long id);
}
