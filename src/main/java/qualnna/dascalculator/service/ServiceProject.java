package qualnna.dascalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.repository.RepositoryProject;

import java.sql.SQLException;
import qualnna.dascalculator.model.Employee;

import java.util.List;

@Service
@Transactional
public class ServiceProject {
    private final RepositoryProject repository;

    public ServiceProject(RepositoryProject repository) {
        this.repository = repository;
    }

    public void deleteEmployee(int employeeId) throws SQLException {
        try {
            repository.deleteEmployee(employeeId);
        }
        catch (SQLException e) {
            System.out.println("Error in deleting employee: " + e.getMessage());;
        }
    }

    public List<String> getSkills() {
        return repository.getSkills();
    }

    public void addEmployee(Employee employee) throws SQLException {
        try {
            repository.addEmployee(employee);
        } catch (SQLException e) {
            System.out.println("Error in creating employee: " + e.getMessage());
        }
    }
}
