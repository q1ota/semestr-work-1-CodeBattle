package org.example.service.impl;

import org.example.entity.ProblemEntity;
import org.example.repository.ProblemRepository;
import org.example.repository.impl.ProblemRepositoryImpl;
import org.example.service.ProblemService;

import java.util.List;
import java.util.Optional;

public class ProblemServiceImpl implements ProblemService {
    private static final ProblemRepository repository = new ProblemRepositoryImpl();

    @Override
    public long save(ProblemEntity task) {
        return repository.save(task);
    }

    @Override
    public List<ProblemEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProblemEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void update(ProblemEntity task) {
        repository.update(task);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}
