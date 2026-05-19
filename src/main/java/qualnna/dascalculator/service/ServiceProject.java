package qualnna.dascalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.exceptions.CouldNotCreateEmployeeException;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.repository.RepositoryProject;

import java.sql.SQLException;
import qualnna.dascalculator.model.Employee;

import java.util.List;

@Service
@Transactional
public class ServiceProject {
    private final RepositoryProject repository;

    ServiceProject(RepositoryProject repository){this.repository = repository;}

    public List<String> readSkills(){return repository.readSkills();}
    public List<Employee> readEmployees(){return repository.readEmployees();}
    public List<Project> readSurfaceInfo(){return repository.readSurfaceInfo();}

    public Project readProjectInfo(int projectID){
        return repository.readProjectInfo(projectID);
    }


    public Project addProject(Project projectToAdd) throws SQLException {
        try {
            return repository.addProject(projectToAdd);
        } catch (SQLException e) {
            throw new InvalidDateException("Start date must be prior to deadline. ");
        }
    }

    public void addEmployee(Employee employee) throws SQLException {
        try {
            repository.addEmployee(employee);
        } catch (SQLException e) {
            throw new CouldNotCreateEmployeeException("Could not create employee. ");
        }
    }
}
