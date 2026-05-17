package qualnna.dascalculator.repository.dataExtractors;

import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.ExternalResource;
import qualnna.dascalculator.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskResultSetExtractor implements ResultSetExtractor<List<Task>> {
    private final JdbcTemplate jdbcTemplate;

    public TaskResultSetExtractor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> extractData(@NonNull ResultSet resultSet) throws SQLException {
        List<Task> foundTasks = new ArrayList<>();
        while(resultSet.next()){
            Task task = new Task(
                    resultSet.getInt("task_id"),
                    resultSet.getString("task_name"),
                    resultSet.getInt("workload")
            );
            task.setExternalResources(findExternalResource(task));
            task.setSkills(findSkills(task));
            task.setAssignments(findAssignments(task));
            foundTasks.add(task);
        }
        return foundTasks;
    }

    private List<ExternalResource> findExternalResource(Task task){
        String SQLExternalResource = """
                select payment_type, price, resource_name, description, source
                from external_resource where task_id = ?;
                """;

        return jdbcTemplate.query(SQLExternalResource, new ExternalResourceRowMapper(), task.getTaskID());
    }
    private List<String> findSkills(Task task){
        String SQLSkills = """
                select skill_name
                from task_skill join task
                on task_skill.task_id = task.task_id
                    and task.task_id = ?
                join skill
                on task_skill.skill_id = skill.skill_id;
                """;

        return jdbcTemplate.query(SQLSkills, new SingleColumnRowMapper<>(), task.getTaskID());
    }
    private List<Assignment> findAssignments(Task task){
        String SQLAssignments = """
                select employee_id, time_spent, completion_date
                from employee_task where task_id = ?;
                """;

        return jdbcTemplate.query(SQLAssignments, new AssignmentResultExtractor(jdbcTemplate), task.getTaskID());
    }

}
