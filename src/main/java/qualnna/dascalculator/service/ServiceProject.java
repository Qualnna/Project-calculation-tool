package qualnna.dascalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.repository.RepositoryProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServiceProject {
    public final RepositoryProject repository;

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

    public List<Employee> fetchEmployees(){
        return repository.fetchEmployees();
    }

    public Employee fetchEmployee(int employeeId) {
        return repository.fetchEmployee(employeeId);
    }

    public void updateEmployee(Employee employee) {
        repository.updateEmployee(employee);
    }

    public List<String> getSkills() {

        return repository.getSkills();
    }
}
