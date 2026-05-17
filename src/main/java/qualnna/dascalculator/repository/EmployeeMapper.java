package qualnna.dascalculator.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeMapper implements ResultSetExtractor<Employee> {
    @Override
    public Employee extractData(ResultSet resultSet) throws SQLException, DataAccessException {
      Employee employee = null;
        while (resultSet.next()) {
            if(employee == null) {
                employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setHourlyRate(resultSet.getFloat("hourly_rate"));
                employee.setAssignedTasks(new ArrayList<>());
                employee.setSkills(new ArrayList<>());
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
                            resultSet.getString("task_name"), resultSet.getInt("workload"));
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
        return  employee;
    };

}
