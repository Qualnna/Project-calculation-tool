package qualnna.dascalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.repository.RepositoryProject;

import java.util.List;

@Service
@Transactional
public class ServiceProject {
    private final RepositoryProject repository;
    public ServiceProject(RepositoryProject repository) {
        this.repository = repository;
    }

    public List<String> getSkills() {
        return repository.getSkills();
    }
}
