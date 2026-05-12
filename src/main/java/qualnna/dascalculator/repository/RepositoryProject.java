package qualnna.dascalculator.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import qualnna.dascalculator.repository.rowMappers.SingleColumnRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryProject {
    private final JdbcTemplate jdbcTemplate;

    public RepositoryProject(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getSkills() {
        String sqlSkills = "select skill_name from skill";
        List<String> skills = jdbcTemplate.query(sqlSkills, new SingleColumnRowMapper());
        List<String> results = new ArrayList<>();

        for(String skill: skills) {
            results.add(skill);
        }
        return results;
    }
}
