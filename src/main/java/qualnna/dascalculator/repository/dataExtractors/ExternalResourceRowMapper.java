package qualnna.dascalculator.repository.dataExtractors;

import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.RowMapper;
import qualnna.dascalculator.model.ExternalResource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExternalResourceRowMapper implements RowMapper<ExternalResource> {

    @Override
    public ExternalResource mapRow(@NonNull ResultSet resultSet, int rowNum) throws SQLException {
        return new ExternalResource(
                resultSet.getString("payment_type"),
                resultSet.getFloat("price"),
                resultSet.getString("resource_name"),
                resultSet.getString("description"),
                resultSet.getString("source")
        );

    }
}
