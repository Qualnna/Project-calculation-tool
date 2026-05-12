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


    @GetMapping("/employees")
    public String employeePage (Model model) throws SQLException {
        model.addAttribute("employees", service.fetchEmployees());
        return "employee-page";
    }


    @PostMapping("/update/{employeeId}")
    public String updateEmployee(@PathVariable int employeeId) {
      //  service.updateEmployee(employeeId);
        return "redirect:/";
    }

}
