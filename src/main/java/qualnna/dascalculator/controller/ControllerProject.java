package qualnna.dascalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.service.ServiceProject;

import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerProject {
    private final ServiceProject service;
    public ControllerProject(ServiceProject service) {this.service = service;}

    @GetMapping("/employee/create")
    public String createEmployee(Model model) {
        Employee newEmployee = new Employee();
        model.addAttribute("newEmployee", newEmployee);
        model.addAttribute("skills", service.getSkills());
        return "create-employee";
    }
    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee newEmployee) {
        service.addEmployee(newEmployee);
        return "redirect:/";
    }

}
