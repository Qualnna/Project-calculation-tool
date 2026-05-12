package qualnna.dascalculator.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.repository.rowMappers.SingleColumnRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryProject {
    private final JdbcTemplate jdbcTemplate;

    public RepositoryProject(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getSkills() {
        String sqlSkills = "select skill_name from skill";
        List<String> skills = jdbcTemplate.query(sqlSkills, new SingleColumnRowMapper());
        List<String> results = new ArrayList<>();

        for(String skill: skills) {
            results.add(skill);
        }
        return results;
    }

    public void addEmployee(Employee employee) {
        String sqlEmployee = """
                insert into employee (employee_name, hourly_rate)
                values (?, ?);
                """;
        jdbcTemplate.update(sqlEmployee, employee.getName(), employee.getHourlyRate());

    }
}
