package qualnna.dascalculator.repository.dataExtractors;

import org.springframework.jdbc.core.RowMapper;
import qualnna.dascalculator.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SurfaceProjectDataRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Project(resultSet.getInt("project_id"),
                resultSet.getString("project_name"),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("deadline").toLocalDate());
    }
}
