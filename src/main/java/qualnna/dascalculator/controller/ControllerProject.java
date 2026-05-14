package qualnna.dascalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerProject {

    final ServiceProject service;

    public ControllerProject(ServiceProject service) {
        this.service = service;
    }

    @PostMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) throws SQLException {
        service.deleteEmployee(employeeId);
        return "redirect:/";
    }

    @GetMapping("/employee/{employeeId}/edit")
    public String employeeView (@PathVariable int employeeId, Model model) {
        Employee employee = service.fetchEmployee(employeeId);
        List<String> skills = service.getSkills();
        model.addAttribute("employee", employee);
        model.addAttribute("skills", skills);
        return "edit-employee";
    }

    @PostMapping("/employee/update")
    public String updateEmployeeAction (@ModelAttribute Employee employee) {
        service.updateEmployee(employee);
        return "redirect:/employees";
    }


    @GetMapping("/employees")
    public String employeePage (Model model) throws SQLException {
        model.addAttribute("employees", service.fetchEmployees());
        return "employee-page";
    }


}
