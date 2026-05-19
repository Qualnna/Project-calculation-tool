package qualnna.dascalculator.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    void fetchEmployees_returnsAllEmployeesWithTasksAndSkills() {
        List<Employee> employees = repository.readEmployees();
        assertEquals(2, employees.size());
        Employee alice = new Employee();
        for (Employee employee : employees) {
            if (employee.getEmployeeName().equals("Alice")){
                alice = employee;
            }
        }

        assertEquals("Alice", alice.getEmployeeName());
        List<String> skills = alice.getSkills();
        assertTrue(skills.containsAll(List.of("Java", "SQL")));
        //assertEquals(2, alice.getAssignedTasks().size());


    }

    @Test
    void fetchEmployee_returnsRightEmployee() {
        List<Employee> employees = repository.readEmployees();
        Employee alice = new Employee();
        for (Employee emp : employees) {
            if (emp.getEmployeeName().equalsIgnoreCase("alice")) {
                alice = emp;
            }
        }

        assertNotNull(alice);
        assertEquals("Alice", alice.getEmployeeName());
        assertEquals(500, alice.getHourlyPayRate());
        assertEquals(List.of("Java", "SQL"), alice.getSkills());
    }

    @Test
    void getSkills() {
        List<String> skills = repository.getSkills();
        assertEquals(List.of("Java", "SQL", "Python"), skills);
    }

    @Test
    void deleteEmployee_removesEmployee() throws SQLException {
        repository.deleteEmployee(1);
        List<Employee> remaining = repository.readEmployees();
        assertEquals(1, remaining.size());
        assertEquals(2, remaining.getFirst().getEmployeeID());
    }

    @Test
    void updateEmployee_changesNameAndHourlyRate() {
        Employee alice = repository.fetchEmployee(1);
        alice.setEmployeeName("Thomas");
        alice.setHourlyPayRate(750);
        repository.updateEmployee(alice);

        Employee thomas = repository.fetchEmployee(1);
        assertEquals("Thomas", thomas.getEmployeeName());
        assertEquals(750, thomas.getHourlyPayRate());
    }

    @Test
    void updateEmployee_handlesEmptySkillList() {
        Employee alice = repository.fetchEmployee(1);
        alice.setSkills(new ArrayList<>());
        repository.updateEmployee(alice);

        Employee reloaded = repository.fetchEmployee(1);
        assertEquals(List.of(), reloaded.getSkills());
    }

    @Test
    void updateEmployee_replaceSkills() {
        Employee alice = repository.fetchEmployee(1);
        alice.setSkills(new ArrayList<>(List.of("Python", "Java")));

        repository.updateEmployee(alice);

        Employee newAlice = repository.fetchEmployee(1);
        // Using set here to ensure that the order that the database returns does not matter
        assertEquals(Set.of("Python", "Java"), new HashSet<>(newAlice.getSkills()));




    }
}