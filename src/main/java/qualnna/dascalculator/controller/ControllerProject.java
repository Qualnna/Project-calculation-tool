package qualnna.dascalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;
import java.util.List;

import org.springframework.ui.Model;
import qualnna.dascalculator.model.Employee;

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

    @GetMapping("/employee/create")
    public String createEmployee(Model model) {
        Employee newEmployee = new Employee();
        model.addAttribute("newEmployee", newEmployee);
        model.addAttribute("skills", service.getSkills());
        return "create-employee";
    }
    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee newEmployee, @ModelAttribute List<String> skills, Model model) {
        try {
        newEmployee.addSkills(skills);
        service.addEmployee(newEmployee);
        return "redirect:/";
        } catch (SQLException e) {
            model.addAttribute("newEmployee", newEmployee);
            model.addAttribute("skills", service.getSkills());
            model.addAttribute("errorMessage", e.getMessage());
            return "create-employee";
        }
    }

}
