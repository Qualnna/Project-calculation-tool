package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.repository.dataExtractors.EmployeeResultSetExtractor;
import qualnna.dascalculator.repository.dataExtractors.ProjectResultSetExctractor;
import qualnna.dascalculator.repository.dataExtractors.SurfaceProjectDataRowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class RepositoryProject {
    public RepositoryProject(JdbcTemplate jdbcTemplate, @Autowired DataSource dataSource) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.connection = dataSource.getConnection();
    }

    private final JdbcTemplate jdbcTemplate;
    private final Connection connection;

    public List<String> readSkills(){
        String SQLGetSkills = """
                select skill_name from skill;
                """;

        return jdbcTemplate.query(SQLGetSkills, new SingleColumnRowMapper<>());
    }

    public List<Employee> readEmployees(){
        String SQLGetEmployees = """
                select employee_name as employee_name,
                       hourly_rate as hourly_rate,
                       skill_name as skill_name
                from employee_skill join employee
                on employee.employee_id = employee_skill.employee_id
                join skill on skill.skill_id = employee_skill.skill_id
                order by employee_name;
                """;

        return jdbcTemplate.query(SQLGetEmployees, new EmployeeResultSetExtractor());
    }

    public List<Project> readSurfaceInfo(){
        String SQLGetProjectNames = """
                select project_id as project_id,
                project_name as name,
                start_date as start_date,
                deadline as deadline from project;
                """;

        return jdbcTemplate.query(SQLGetProjectNames, new SurfaceProjectDataRowMapper());
    }

    public Project readProjectInfo(int projectID){
        String SQLGetProjectNames = """
                select project_id,
                project_name,
                start_date,
                deadline from project
                where project_id = ?;
                """;

        return jdbcTemplate.query(SQLGetProjectNames, new ProjectResultSetExctractor(jdbcTemplate, connection), projectID);
    }


    public Project addProject(Project project) throws SQLException {
        String SQLAddProject = """
                insert into project (project_name, start_date, deadline) values(?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(SQLAddProject, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getName());
        statement.setObject(2, project.getStartdate());
        statement.setObject(3, project.getDeadline());

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
}
