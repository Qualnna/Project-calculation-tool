package qualnna.dascalculator.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeListMapper implements ResultSetExtractor<List<Employee>> {
    @Override
    public ArrayList<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        while (resultSet.next()) {
            int employeeId = resultSet.getInt("employee_id");

            Employee employee = employeeMap.get(employeeId);
            if(employee == null) {
                employee = new Employee();
                employee.setEmployeeId(employeeId);
                employee.setName(resultSet.getString("employee_name"));
                employee.setHourlyRate(resultSet.getFloat("hourly_rate"));
                employee.setAssignedTasks(new ArrayList<>());
                employee.setSkills(new ArrayList<>());
                employeeMap.put(employeeId, employee);
            }

            int taskId = resultSet.getInt("task_id");
            if (taskId > 0) {
                boolean taskAlreadyAssigned = false;
                for (Task existingTask : employee.getAssignedTasks()) {
                    if (existingTask.taskId() == taskId) {
                        taskAlreadyAssigned = true;
                        break;
                    }
                }
                if (!taskAlreadyAssigned) {
                    Task newTask = new Task(taskId, resultSet.getInt("sub_id"),
                            resultSet.getString("task_name"), resultSet.getDouble("workload"));
                    employee.getAssignedTasks().add(newTask);
                }

            }
            String skillName = resultSet.getString("skill_name");
            if (skillName != null) {
                boolean skillAlreadyAdded = false;
                for (String existingSkill : employee.getSkills()) {
                    if (existingSkill.equals(skillName)) {
                        skillAlreadyAdded = true;
                        break;
                    }
                }
                if (!skillAlreadyAdded) {
                    employee.getSkills().add(skillName);
                }
            }
        }
        return new ArrayList<>(employeeMap.values());
    };
 }
