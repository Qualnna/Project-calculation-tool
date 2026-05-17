package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import javax.sql.DataSource;
import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Transactional
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

    public List<Employee> fetchEmployees() {
        String sql = """
                        select e.employee_id,
                        e.employee_name,
                        e.hourly_rate,
                        t.task_id,
                        t.sub_id,
                        t.workload,
                        t.task_name,
                        s.skill_id,
                        s.skill_name
                        from employee e
                        LEFT JOIN employee_task et ON e.employee_id = et.employee_id
                        LEFT JOIN task t ON et.task_id = t.task_id
                        LEFT JOIN employee_skill esk ON e.employee_id = esk.employee_id
                        LEFT JOIN skill s ON esk.skill_id = s.skill_id
                
                        """;

        return jdbcTemplate.query(sql, new EmployeeListMapper());
    }

    public Employee fetchEmployee(int employeeId) {
        String sql = """
                        select e.employee_id,
                        e.employee_name,
                        e.hourly_rate,
                        t.task_id,
                        t.sub_id,
                        t.workload,
                        t.task_name,
                        s.skill_id,
                        s.skill_name
                        from employee e
                        LEFT JOIN employee_task et ON e.employee_id = et.employee_id
                        LEFT JOIN task t ON et.task_id = t.task_id
                        LEFT JOIN employee_skill esk ON e.employee_id = esk.employee_id
                        LEFT JOIN skill s ON esk.skill_id = s.skill_id
                        WHERE e.employee_id = ?

                
                """;
          return jdbcTemplate.query(sql, ps -> ps.setInt(1, employeeId), new EmployeeMapper());
    }


    public List<String> getSkills() {
        String sql = "select skill_name from skill";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public void updateEmployee(Employee employee) {
        jdbcTemplate.update(
                "UPDATE employee SET employee_name = ?, hourly_rate = ? WHERE employee_id = ?",
                employee.getName(), employee.getHourlyRate(), employee.getEmployeeId()
        );
            jdbcTemplate.update("DELETE FROM employee_skill WHERE employee_id = ?", employee.getEmployeeId());
            for (String skill : employee.getSkills()) {
                jdbcTemplate.update(
                    """
                    INSERT INTO employee_skill(employee_id, skill_id)
                    SELECT ?, skill_id FROM skill WHERE skill_name = ?
                    """, employee.getEmployeeId(), skill);
            }

    }
}
