package qualnna.dascalculator.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.exceptions.AssignmentNotFoundException;
import qualnna.dascalculator.exceptions.InvalidAssigmentException;
import qualnna.dascalculator.exceptions.CouldNotCreateEmployeeException;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.exceptions.NoSuchEmployee;
import qualnna.dascalculator.exceptions.ProjectNotFoundException;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.model.Task;
import qualnna.dascalculator.model.SubProject;
import qualnna.dascalculator.repository.RepositoryProject;

import java.sql.SQLException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
public class ServiceProject {
    private final RepositoryProject repository;

    ServiceProject(RepositoryProject repository){this.repository = repository;}

    public List<String> readSkills(){return repository.readSkills();}
    public List<Employee> readEmployees(){return repository.readEmployees();}
    public List<Project> readSurfaceInfo(){return repository.readSurfaceInfo();}

    public Project readProjectInfo(int projectID){
        try {
            return repository.readProjectInfo(projectID);
        } catch (DataAccessException e) {
            throw new ProjectNotFoundException("Something went wrong while trying to access the project.");
        }
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

    public void addSubProject(SubProject subProject, int projectID) throws SQLException {
        try {
            repository.addSubProject(subProject, projectID);
        } catch (SQLException e) {
            throw new InvalidDateException("Deadline for Sub Project should be within the timeframe of Project. ");
        }
    }

    public void addAssignment(LocalDate subDeadline, int taskID, Assignment assignment){
        try {
            repository.addAssignment(subDeadline, taskID, assignment);
        } catch (DataAccessException e) {
            throw new InvalidAssigmentException("Could not create assignment. ");
        }
    }

    public void deleteAssignment(int employeeID, int taskID){
        try {
            repository.deleteAssignment(employeeID, taskID);
        } catch (DataAccessException e) {
            throw new AssignmentNotFoundException("Failed to delete Assignment for employeeID = " +
                    employeeID + "and taskID = " + taskID);
        }
    }

    public void deleteEmployee(int employeeId) throws SQLException {
        try {
            repository.deleteEmployee(employeeId);
        }
        catch (SQLException e) {
            throw new NoSuchEmployee("Error in deleting employee with ID:" + employeeId);
        }
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

    public void createTask(Task task, int subProjectId) {
        repository.createTask(task, subProjectId);
    }

    public void removeTask(int taskID) {
        repository.removeTask(taskID);
    }
}

