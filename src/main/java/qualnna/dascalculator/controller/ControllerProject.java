package qualnna.dascalculator.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerProject {

    private final ServiceProject service;
    private List<Employee> employees;
    private List<String> skills;
    private Project project;

    ControllerProject(ServiceProject service){this.service = service;}

    private boolean validateSession(HttpSession session){
        return session!=null;
    }

    @GetMapping("/addProject")
    public String addProjectGet(Model model, HttpSession session){
        Project projectToAdd = new Project();
        model.addAttribute("project", projectToAdd);

        return "add-project";
    }

    @PostMapping("/addProject")
    public String addProjectPost(@ModelAttribute Project projectToAdd, Model model, HttpSession session){
        try {
            this.project = service.addProject(projectToAdd);
            session.setAttribute("project", this.project);
            return "redirect:/show-project";
        } catch (InvalidDateException e) {
            model.addAttribute("project", projectToAdd);
            model.addAttribute("errorMessage", e.getMessage());
            return "add-project";
        } catch (SQLException e) {
            return "error";
        }
    }
}
