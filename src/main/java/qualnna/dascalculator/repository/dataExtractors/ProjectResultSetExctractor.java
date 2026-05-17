package qualnna.dascalculator.repository.dataExtractors;

import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.model.SubProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectResultSetExctractor implements ResultSetExtractor<Project> {
    private final JdbcTemplate jdbcTemplate;
    private final Connection connection;

    public ProjectResultSetExctractor(JdbcTemplate jdbcTemplate, Connection connection) {
        this.jdbcTemplate = jdbcTemplate;
        this.connection = connection;
    }

    @Override
    public Project extractData(@NonNull ResultSet resultSet) throws SQLException {


        Project foundProject = new Project(
                resultSet.getInt("project_id"),
                resultSet.getString("project_name"),
                resultSet.getDate("startdate").toLocalDate(),
                resultSet.getDate("deadline").toLocalDate());

        foundProject.setSubProjects(readSubProjects(foundProject.getId()));

        foundProject.setTotalRequiredWorkload(readRequiredWorkload(foundProject));
        foundProject.setTotalAssignedWorkload(readAssignedWorkload(foundProject));
        foundProject.setEstimatedPrice(readEstimatedPrice(foundProject));
        return foundProject;
    }

    private int readRequiredWorkload(Project project) throws SQLException{
        String SQLSumTotalRequiredWorkload = """
                select sum(workload) from task where sub_id = ?;
                """;
        return batchSum(project, SQLSumTotalRequiredWorkload);
    }

    private int readAssignedWorkload(Project project) throws SQLException{
        String SQLSumTotalAssignedWorkload = """
                select sum(time_spent) from employee_task join task
                on task.task_id = employee_task.task_id and task.sub_id = ?;
                """;
        return batchSum(project, SQLSumTotalAssignedWorkload);
    }


    private int batchSum(Project project, String SQLString) throws SQLException {
        int totalTime = 0;
        PreparedStatement statement = connection.prepareStatement(SQLString);
        for(SubProject subProject: project.getSubProjects()){
            statement.setInt(1, subProject.getSubProjectID());
            statement.addBatch();
        }

        ResultSet result = statement.executeQuery();

        while(result.next()){
            totalTime += result.getInt((1));
        }

        return totalTime;
    }

    private float readEstimatedPrice(Project project) throws SQLException{
        float totalPrice = 0;
        String SQLEstimatedPrice = """
                select sum(cast(employee_task.time_spent as float) * employee.hourly_rate)
                from employee_task
                join task on task.task_id = employee_task.task_id and task.sub_id = ?
                join employee on employee.employee_id = employee_task.employee_id;
                """;
        PreparedStatement statement = connection.prepareStatement(SQLEstimatedPrice);
        for(SubProject subProject: project.getSubProjects()){
            statement.setInt(1, subProject.getSubProjectID());
            statement.addBatch();
        }
        ResultSet result = statement.executeQuery();

        while(result.next()){
            totalPrice+=result.getFloat(1);
        }

        return totalPrice;
    }


    private List<SubProject> readSubProjects(int projectID){
        String SQLSubProjects = """
                select sub_id as sub_id,
                       sub_name as sub_name,
                       sub_deadline as sub_deadline
                from sub_project where project_id = ?;
                """;

        return jdbcTemplate.query(SQLSubProjects, new SubProjectResultSetExtractor(jdbcTemplate), projectID);
    }
}
