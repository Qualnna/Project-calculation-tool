package qualnna.dascalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.repository.RepositoryProject;

import java.sql.SQLException;

@Service
@Transactional
public class ServiceProject {
    private final RepositoryProject repository;

    ServiceProject(RepositoryProject repository){this.repository = repository;}

    public Project addProject(Project projectToAdd) throws SQLException {
        try {
            return repository.addProject(projectToAdd);
        } catch (SQLException e) {
            throw new InvalidDateException("Start date must be prior to deadline. ");
        }
    }
}
