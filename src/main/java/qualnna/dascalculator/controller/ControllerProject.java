package qualnna.dascalculator.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.exceptions.InvalidDateException;
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

    private boolean isSessionInvalid(HttpSession session){
        return session.getAttribute("employees")==null||session.getAttribute("skills")==null;
    }

    @GetMapping("/")
    public String startPage(Model model, HttpSession session){
        if(isSessionInvalid(session)){
            this.employees = service.readEmployees();
            this.skills = service.readSkills();
            session.setAttribute("employees", this.employees);
            session.setAttribute("skills", this.skills);
        }
        model.addAttribute("projects", service.readSurfaceInfo());

        return "home-page";
    }

    @PostMapping("/readProjectInfo/{projectID}")
    public String readProjectInfo(@PathVariable int projectID, Model model, HttpSession session){
        this.project = service.readProjectInfo(projectID);
        session.setAttribute("project", this.project);
        return "redirect:/show-project";
    }

    @GetMapping("/employee/create")
    public String createEmployee(Model model) {
        Employee newEmployee = new Employee();
        model.addAttribute("newEmployee", newEmployee);
        return "create-employee";
    }
    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee newEmployee, Model model) {
        try {
        service.addEmployee(newEmployee);
        return "redirect:/";
        } catch (SQLException e) {
            model.addAttribute("newEmployee", newEmployee);
            model.addAttribute("errorMessage", e.getMessage());
            return "create-employee";
        }
    }

    @GetMapping("/addProject")
    public String addProjectGet(Model model, HttpSession session){
        if(isSessionInvalid(session)){
            return "redirect:/";
        }
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
    @GetMapping("/employee/{employeeId}/edit")
    public String employeeView (@PathVariable int employeeId, Model model) {
        model.addAttribute("employee", service.fetchEmployee(employeeId));
        model.addAttribute("skills", service.getSkills());
        return "edit-employee";
    }

    @PostMapping("/employee/update")
    public String updateEmployeeAction (@ModelAttribute Employee employee) {
        service.updateEmployee(employee);
        return "redirect:/employees";
    }

    @PostMapping("/employee/delete/{employeeID}")
    public String deleteEmployee (@PathVariable int employeeID) throws SQLException {
        service.deleteEmployee(employeeID);
        return "redirect:/employees";
    }


    @GetMapping("/employees")
    public String employeePage (HttpSession session) {
        session.setAttribute("employees", service.readEmployees());
        return "employee-page";
    }




}
