package qualnna.dascalculator.repository.dataExtractors;

import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> foundEmployees = new ArrayList<>();
        Employee employee = new Employee();
        while(resultSet.next()){
            if(employee.getEmployeeName()==null || !employee.getEmployeeName().equals(resultSet.getString("employee_name"))){
                employee = new Employee(
                        resultSet.getString("employee_name"),
                        resultSet.getFloat("hourly_rate")
                );
                foundEmployees.add(employee);
            }
            foundEmployees.getLast().addSkill(resultSet.getString("skill_name"));
        }
        return foundEmployees;
    }
}
