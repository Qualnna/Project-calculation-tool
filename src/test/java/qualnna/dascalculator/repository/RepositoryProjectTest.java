package qualnna.dascalculator.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import qualnna.dascalculator.model.Project;

import java.sql.Date;
import java.sql.SQLException;

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
    void addProjectInvalidDates() throws Exception{
        Project projectToInsert = new Project();
        projectToInsert.setName("test project");
        projectToInsert.setStartdate(Date.valueOf("2026-12-24"));
        projectToInsert.setDeadline(Date.valueOf("2026-11-24"));

        assertThrows(SQLException.class, () -> {repository.addProject(projectToInsert);});
    }
}