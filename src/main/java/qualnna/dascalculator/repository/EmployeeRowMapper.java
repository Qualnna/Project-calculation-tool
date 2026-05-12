//package qualnna.dascalculator.repository;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import qualnna.dascalculator.model.Employee;
//import qualnna.dascalculator.model.Task;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmployeeRowMapper implements ResultSetExtractor<List<Employee>> {
//    @Override
//    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//        List<Employee> employees = new ArrayList<>();
//        Employee employee = new Employee();
//
//        while(resultSet.next()) {
//            int id = resultSet.getInt("employee_id");
//
//                employee.setEmployeeId(id);
//                employee.setName(resultSet.getString("employee_name"));
//                employee.setHourlyRate(resultSet.getDouble("hourly_rate"));
//                employee.setSkills(new ArrayList<>());
//                employee.setAssignedTasks(new ArrayList<>());
//                employees.add(employee);
//
//        }
//        int taskId = resultSet.getInt("task_id");
//        if (employee)
//
//
//        return employees;
//
//        }

