package qualnna.dascalculator.repository.dataExtractors;

import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.SubProject;
import qualnna.dascalculator.model.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubProjectResultSetExtractor implements ResultSetExtractor<List<SubProject>> {
    private final JdbcTemplate jdbcTemplate;

    public SubProjectResultSetExtractor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SubProject> extractData(@NonNull ResultSet resultSet) throws SQLException {
        List<SubProject> foundSubProjects = new ArrayList<>();
        while(resultSet.next()){
            SubProject subProject = new SubProject(
                    resultSet.getInt("sub_id"),
                    resultSet.getString("sub_name"),
                    resultSet.getDate("sub_deadline").toLocalDate());

            subProject.setTasks(findTasks(subProject));
            foundSubProjects.add(subProject);
        }
        return foundSubProjects;
    }

    private List<Task> findTasks(SubProject subProject){
        String SQLTask = """
                select task_id, task_name, workload from task where sub_id = ?;
                """;

        return jdbcTemplate.query(SQLTask, new TaskResultSetExtractor(jdbcTemplate), subProject.getSubProjectID());
    }
}
