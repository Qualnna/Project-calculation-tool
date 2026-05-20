package qualnna.dascalculator.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts ="classpath:H2Schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:H2Data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RepositoryProjectTest {

    @Autowired
    private RepositoryProject repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProject() throws Exception {
        Project projectToInsert = new Project();
        projectToInsert.setName("test Project");
        projectToInsert.setStartdate(LocalDate.parse("2026-11-24"));
        projectToInsert.setDeadline(LocalDate.parse("2026-12-24"));

        Project projectWithID = repository.addProject(projectToInsert);
    }

    @Test
    void insertAndReadTwoSkills() {
        repository.insertSkill("HTML");
        repository.insertSkill("MySQL");
        List<String> skills = repository.readSkills();

        assertNotNull(skills);
        assertFalse(skills.isEmpty());
        assertTrue(skills.contains("HTML"));
        assertEquals(5, (skills.size()));
    }

    @Test
    void insertEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeName("Test Employee");
        newEmployee.setHourlyPayRate(900);
        // make the assertions when there is a read method for employee
    }

    void addProjectInvalidDates() throws Exception{
        Project projectToInsert = new Project();
        projectToInsert.setName("test project");
        projectToInsert.setStartdate(LocalDate.parse("2026-12-24"));
        projectToInsert.setDeadline(LocalDate.parse("2026-11-24"));

        assertThrows(SQLException.class, () -> {repository.addProject(projectToInsert);});
    }

    @Test
    void readAllEmployees() throws Exception{
        List<Employee> employeeList = repository.readEmployees();
    }

    @Test
    void readAllSkills() throws Exception{
        List<String> skills = repository.readSkills();
    }

    @Test
    void readSurfaceInfo() throws Exception{
        List<Project> projects= repository.readSurfaceInfo();
    }

    @Test
    void readProject() throws Exception{
        Project foundProject = repository.readProjectInfo(1);
    }

    @Test
    void addAssignment(){
        Employee employeeToAssign = repository.readEmployees().getLast();
        Assignment assignmentToAdd = new Assignment(LocalDate.parse("2026-06-28"), 5, employeeToAssign);
        repository.addAssignment(LocalDate.parse("2026-06-30")
                , 11, assignmentToAdd);
    }

    @Test
    void addBadAssignment(){
        Employee employeeToAssign = repository.readEmployees().getFirst();
        Assignment assignmentToAdd = new Assignment(LocalDate.parse("2026-06-28"), 5, employeeToAssign);
        assertThrows(DataAccessException.class, ()->{repository.addAssignment(LocalDate.parse("2026-06-30")
                , 12, assignmentToAdd);});

    }

    @Test
    void deleteAssignment(){
        repository.deleteAssignment(1, 10);
    }

}