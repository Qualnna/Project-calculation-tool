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

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatTemporal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts ="classpath:H2Schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = "classpath:H2Data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        projectToInsert.setStartdate(Date.valueOf("2026-11-24"));
        projectToInsert.setDeadline(Date.valueOf("2026-12-24"));

        Project projectWithID = repository.addProject(projectToInsert);
    }

    @Test
    void insertAndReadTwoSkills() {
        repository.insertSkill("HTML");
        repository.insertSkill("MySQL");
        List<String> skills = repository.getSkills();

        assertThat(skills).isNotNull();
        assertFalse(skills.isEmpty());
        assertThat(skills.get(0)).isEqualTo("HTML");
        assertThat(skills.size()).isEqualTo(2);
    }

    @Test
    void insertEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setName("Test Employee");
        newEmployee.setHourlyRate(900);
        // make the assertions when there is a read method for employee
    }

}