package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.model.Task;

import javax.sql.DataSource;
import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Employee> fetchEmployees() throws SQLException {
        List<String> skills;
         List<Task> assignedTasks;

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
                        INNER JOIN employee_task et ON e.employee_id = et.employee_id
                        INNER JOIN task t ON et.task_id = t.task_id
                        INNER JOIN employee_skill esk ON e.employee_id = esk.employee_id
                        INNER JOIN skill s ON esk.skill_id = s.skill_id;
                
                        """;

        return jdbcTemplate.query(sql, resultSet -> {
            Map<Integer, Employee> employeeMap = new HashMap<>();
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");

                Employee employee = employeeMap.get(employeeId);
                if(employee == null) {
                    employee = new Employee();
                    employee.setEmployeeId(employeeId);
                    employee.setName(resultSet.getString("employee_name"));
                    employee.setHourlyRate(resultSet.getFloat("hourly_rate"));
                    employee.setAssignedTasks(new ArrayList<Task>());
                    employee.setSkills(new ArrayList<String>());
                    employeeMap.put(employeeId, employee);
                }

                int taskId = resultSet.getInt("task_id");
                if (taskId > 0) {
                    boolean taskAlreadyAssigned = false;
                    for (Task existingTask : employee.getAssignedTasks()) {
                        if (existingTask.taskId() == taskId) {
                            taskAlreadyAssigned = true;
                            break;
                        }
                    }
                    if (!taskAlreadyAssigned) {
                        Task newTask = new Task(taskId, resultSet.getInt("sub_id"),
                                resultSet.getString("task_name"), resultSet.getDouble("workload"));
                        employee.getAssignedTasks().add(newTask);
                    }

                }
                String skillName = resultSet.getString("skill_name");
                if (skillName != null) {
                    boolean skillAlreadyAdded = false;
                    for (String existingSkill : employee.getSkills()) {
                        if (existingSkill.equals(skillName)) {
                            skillAlreadyAdded = true;
                            break;
                        }
                    }
                    if (!skillAlreadyAdded) {
                        employee.getSkills().add(skillName);
                    }
                }
            }
            return new ArrayList<>(employeeMap.values());
        });

    }
}
