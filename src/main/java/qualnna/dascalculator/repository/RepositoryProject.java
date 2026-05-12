package qualnna.dascalculator.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class RepositoryProject {
    public RepositoryProject(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        if (jdbcTemplate.getDataSource() != null) {
            this.connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        }
        //Replace with custom exception
        else{throw new DataSourceLookupFailureException("Datasource error");}
    }

    private final JdbcTemplate jdbcTemplate;
    private final Connection connection;

    public void deleteEmployee(int employeeId) throws SQLException {
        String sql = "delete from employee where employee_id = ?";
        jdbcTemplate.update(sql, employeeId);
    }
}
