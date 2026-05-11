package qualnna.dascalculator.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.model.Project;

import javax.sql.DataSource;
import java.sql.Connection;

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
}
