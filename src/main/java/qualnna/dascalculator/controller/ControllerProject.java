package qualnna.dascalculator.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;

@Controller
@RequestMapping("/")
public class ControllerProject {

    private final ServiceProject service;

    ControllerProject(ServiceProject service){this.service = service;}

    @GetMapping("/addProject")
    public String addProjectGet(Model model, HttpSession session){
        Project projectToAdd = new Project();
        model.addAttribute("project", projectToAdd);

        return "add-project";
    }

    @PostMapping("/addProject")
    public String addProjectPost(@ModelAttribute Project projectToAdd, Model model, HttpSession session){
        try {
            model.addAttribute("project", service.addProject(projectToAdd));
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
