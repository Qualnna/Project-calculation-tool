package qualnna.dascalculator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.model.SubProject;
import qualnna.dascalculator.repository.dataExtractors.EmployeeResultSetExtractor;
import qualnna.dascalculator.repository.dataExtractors.ProjectResultSetExctractor;
import qualnna.dascalculator.repository.dataExtractors.SurfaceProjectDataRowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


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


    //This method isn't in use yet. it was made for a test.
    public void insertSkill (String skill) {
        String sqlInsert = """
                insert into skill (skill_name)
                values (?)
                """;
        jdbcTemplate.update(sqlInsert, skill);
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
}
