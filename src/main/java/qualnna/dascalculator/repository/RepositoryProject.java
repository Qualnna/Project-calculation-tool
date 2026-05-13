package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Project;

import javax.sql.DataSource;
import java.sql.*;

import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.repository.rowMappers.SingleColumnRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryProject {
    public RepositoryProject(JdbcTemplate jdbcTemplate, @Autowired DataSource dataSource) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.connection = dataSource.getConnection();
    }

    private final JdbcTemplate jdbcTemplate;
    private final Connection connection;

    public Project addProject(Project project) throws SQLException {
        String SQLAddProject = """
                insert into project (project_name, start_date, deadline) values(?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(SQLAddProject, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getName());
        statement.setDate(2, (Date) project.getStartdate());
        statement.setDate(3, (Date) project.getDeadline());

        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        project.setId(keys.getInt("project_id"));

        return project;
    }
    public void deleteEmployee(int employeeId) throws SQLException {
        String sql = "delete from employee where employee_id = ?";
        jdbcTemplate.update(sql, employeeId);
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
