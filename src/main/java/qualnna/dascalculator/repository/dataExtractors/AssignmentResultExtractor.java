package qualnna.dascalculator.repository.dataExtractors;

import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentResultExtractor implements ResultSetExtractor<List<Assignment>> {
    private final JdbcTemplate jdbcTemplate;

    public AssignmentResultExtractor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Assignment> extractData(@NonNull ResultSet resultSet) throws SQLException {
        List<Assignment> foundAssignments = new ArrayList<>();
        while(resultSet.next()){
            Assignment assignment = new Assignment(
                    resultSet.getDate("sub_deadline").toLocalDate(),
                    resultSet.getInt("time_spent")
            );
            assignment.setAssignedEmployee(findAssignedEmployee(resultSet.getInt("employee_id")));

            foundAssignments.add(assignment);
        }
        return foundAssignments;
    }

    private Employee findAssignedEmployee(int employeeID){
        String SQLGetEmployees = """
                select employee.employee_id,
                       employee_name as employee_name,
                       hourly_rate as hourly_rate,
                       skill_name as skill_name
                from employee_skill join employee
                on employee.employee_id = employee_skill.employee_id
                    and employee.employee_id = ?
                join skill on skill.skill_id = employee_skill.skill_id
                order by employee_name;
                """;

        return jdbcTemplate.query(SQLGetEmployees, new EmployeeResultSetExtractor(), employeeID).getFirst();
    }
}
