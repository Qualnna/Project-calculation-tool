package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.*;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.repository.dataExtractors.EmployeeResultSetExtractor;
import qualnna.dascalculator.repository.dataExtractors.ProjectResultSetExctractor;
import qualnna.dascalculator.repository.dataExtractors.SurfaceProjectDataRowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


import java.time.LocalDate;
import java.util.List;

@Repository
public class RepositoryProject {
    private final JdbcTemplate jdbcTemplate;
    private final Connection connection;

    public RepositoryProject(JdbcTemplate jdbcTemplate, @Autowired DataSource dataSource) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.connection = dataSource.getConnection();
    }


    public List<String> readSkills(){
        String SQLGetSkills = """
                select skill_name from skill;
                """;

        return jdbcTemplate.query(SQLGetSkills, new SingleColumnRowMapper<>());
    }

    public List<Employee> readEmployees(){
        String SQLGetEmployees = """
                select employee.employee_id as employee_id,
                       employee_name as employee_name,
                       hourly_rate as hourly_rate,
                       skill_name as skill_name
                from employee_skill join employee
                on employee.employee_id = employee_skill.employee_id
                join skill on skill.skill_id = employee_skill.skill_id
                order by employee_id;
                """;

        return jdbcTemplate.query(SQLGetEmployees, new EmployeeResultSetExtractor());
    }

    public List<Project> readSurfaceInfo(){
        // Using name as an alias for the project_name will make this fail
        String SQLGetProjectNames = """
                select project_id as project_id,
                project_name,
                start_date as start_date,
                deadline as deadline from project;
                """;

        return jdbcTemplate.query(SQLGetProjectNames, new SurfaceProjectDataRowMapper());
    }

    public Project readProjectInfo(int projectID) throws DataAccessException {
        String SQLGetProjectNames = """
                select project_id,
                project_name,
                start_date,
                deadline from project
                where project_id = ?
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
        project.setId(keys.getInt(1));

        return project;
    }
    public void deleteEmployee(int employeeId) throws SQLException {
        String sql = "delete from employee where employee_id = ?";
        jdbcTemplate.update(sql, employeeId);
    }


    //This method isn't in use yet. it was made for a test.
    public void insertSkill (String skill) {
        String sqlInsert = """
                insert into skill (skill_name)
                values (?)
                """;
        jdbcTemplate.update(sqlInsert, skill);
    }

    public void createTask(Task task, int subProjectId) throws DataAccessException {
        String sql = "INSERT INTO task (sub_id, task_name, workload) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subProjectId);
            ps.setString(2, task.getTaskName());
            ps.setInt(3, task.getWorkload());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        int newTaskId = generatedId.intValue();

        String taskSQL = "INSERT INTO task_skill (task_id, skill_id) SELECT ?, skill_id FROM skill WHERE skill_name = ?";
        for (String skillName : task.getSkills())
        {
            jdbcTemplate.update(taskSQL, newTaskId, skillName);
        }
    }

    public void addEmployee(Employee employee) throws SQLException{
        String sqlEmployee = """
                insert into employee (employee_name, hourly_rate)
                values (?, ?);
                """;

        PreparedStatement updateEmp = connection.prepareStatement(sqlEmployee);
        updateEmp.setString(1, employee.getEmployeeName());
        updateEmp.setFloat(2, employee.getHourlyPayRate());
        updateEmp.executeUpdate();

        addEmpSkill(employee);
    }

    public void addEmpSkill(Employee employee) throws SQLException {
        List<String> skills = employee.getSkills();
        String SQLEmployeeSkills = """
                insert into employee_skill (employee_id, skill_id) select e.employee_id, s.skill_id
                from (select employee_id from employee where employee_name = ?) as e
                cross join (select skill_id from skill where skill_name = ?) as s;
                """;
        PreparedStatement prepStmt = connection.prepareStatement(SQLEmployeeSkills);
        for(String skill: skills) {
            prepStmt.setString(1, employee.getEmployeeName());
            prepStmt.setString(2, skill);
            prepStmt.addBatch();
        }
        prepStmt.executeBatch();
    }

    public void addSubProject(SubProject subProject, int projectId) throws SQLException {
        String SQLAddSubProject = """
                insert into sub_project (sub_name, sub_deadline, project_id)
                values (?, ?, ?);
                """;
        PreparedStatement statement = connection.prepareStatement(SQLAddSubProject);
        statement.setString(1, subProject.getSubProjectName());
        statement.setObject(2, subProject.getSubProjectDeadline());
        statement.setInt(3, projectId);
        statement.executeUpdate();
    }

    public Employee fetchEmployee(int employeeId) {
        String sql = """
            SELECT employee.employee_id AS employee_id,
                   employee_name AS employee_name,
                   hourly_rate AS hourly_rate,
                   skill_name AS skill_name
            FROM employee 
            LEFT JOIN employee_skill
            LEFT JOIN skill ON skill.skill_id = employee_skill.skill_id
            ON employee.employee_id = employee_skill.employee_id
            WHERE employee.employee_id = ?
            ORDER BY employee_id;
            """;

        List<Employee> result = jdbcTemplate.query(sql, new EmployeeResultSetExtractor(), employeeId);
        return result.isEmpty() ? null : result.getFirst();
    }

    public List<String> getSkills() {
        String sql = "select skill_name from skill";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public void updateEmployee(Employee employee) {
        jdbcTemplate.update(
                "UPDATE employee SET employee_name = ?, hourly_rate = ? WHERE employee_id = ?",
                employee.getEmployeeName(), employee.getHourlyPayRate(), employee.getEmployeeID()
        );
            jdbcTemplate.update("DELETE FROM employee_skill WHERE employee_id = ?", employee.getEmployeeID());
            for (String skill : employee.getSkills()) {
                jdbcTemplate.update(
                    """
                    INSERT INTO employee_skill(employee_id, skill_id)
                    SELECT ?, skill_id FROM skill WHERE skill_name = ?
                    """, employee.getEmployeeID(), skill);
            }

    }

    public void removeTask(int taskID) {
        String sql = "DELETE FROM task WHERE task_id = ?";
        jdbcTemplate.update(sql, taskID);
    }

    public void addAssignment(LocalDate subDeadline, int taskID, Assignment assignment) throws DataAccessException{
        String SQLAddAssignment = """
                insert into employee_task(employee_id, task_id, sub_deadline, time_spent, completion_date)
                values (?, ?, ?, ?, ?);
                """;

        jdbcTemplate.update(SQLAddAssignment, ps -> {
            ps.setInt(1, assignment.getAssignedEmployee().getEmployeeID());
            ps.setInt(2, taskID);
            ps.setDate(3, Date.valueOf(subDeadline));
            ps.setInt(4, assignment.getTimeSpent());
            ps.setDate(5, Date.valueOf(assignment.getCompletionDate()));});

        }

    public void deleteAssignment(int employeeID, int taskID) throws DataAccessException{
        String SQLDeleteAssignment = """
                delete from employee_task where employee_id = ? and task_id = ?;
                """;

        jdbcTemplate.update(SQLDeleteAssignment, ps -> {
            ps.setInt(1, employeeID);
            ps.setInt(2, taskID);
        });
    }

    public void deleteSubProject(int subProjectID) throws DataAccessException  {
        String SQL = "delete from sub_project where sub_id = ?";
        jdbcTemplate.update(SQL, subProjectID);
    }


}
