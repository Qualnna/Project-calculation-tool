package qualnna.dascalculator.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qualnna.dascalculator.exceptions.*;
import qualnna.dascalculator.model.Assignment;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.model.Task;
import qualnna.dascalculator.model.SubProject;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;
import java.time.LocalDate;
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
        return "redirect:/employees";
        } catch (CouldNotCreateEmployeeException e) {
            model.addAttribute("newEmployee", newEmployee);
            model.addAttribute("errorMessage", e.getMessage());
            return "create-employee";
        } catch (SQLException e) {
            return "error";
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
            return "redirect:/readProjectInfo/" + project.getId();
        } catch (InvalidDateException e) {
            model.addAttribute("project", projectToAdd);
            model.addAttribute("errorMessage", e.getMessage());
            return "add-project";
        } catch (SQLException e) {
            return "error";
        }
    }

    @PostMapping("/delete/project")
    public String deleteProject(@RequestParam("projectId") int projectId, RedirectAttributes redirect,Model model, HttpSession session) {
        try {
            service.deleteProject(projectId);
            session.removeAttribute("project");
        } catch (ProjectNotFoundException e) {
            redirect.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
    @GetMapping("/employee/{employeeId}/edit")
    public String employeeView (@PathVariable int employeeId, Model model) {
        model.addAttribute("employee", service.fetchEmployee(employeeId));
        model.addAttribute("skills", service.readSkills());
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
    @GetMapping("/create/task/{subProjectID}")
    public String showCreateTaskForm(@PathVariable int subProjectID, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("subProjectID", subProjectID);
        return "create-task";
    }

    @PostMapping("/create/task/{subProjectID}")
    public String taskCreation(@ModelAttribute  Task task, @PathVariable int subProjectID, HttpSession session, Model model) {
        try {
            Project project = (Project) session.getAttribute("project");
            project.addTaskToSubProject(task, subProjectID);
            service.createTask(task, subProjectID);
            return "redirect:/readProjectInfo/" + subProjectID + "/tasks";
        } catch (CouldNotCreateTask e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/readProjectInfo/" + subProjectID + "/tasks";

    }

    @PostMapping("/delete/task/{subProjectID}")
    public String taskDeletion(@ModelAttribute  Task task, @PathVariable int subProjectID, HttpSession session) {
        Project project = (Project) session.getAttribute("project");
        project.deleteTaskFromProject(task, subProjectID);
        service.removeTask(task.getTaskID());
        Project updatedProject = service.readProjectInfo(
                ((Project) session.getAttribute("project")).getId()
        );
        session.setAttribute("project", updatedProject);
        return "redirect:/readProjectInfo/" + subProjectID + "/tasks";
    }


    @GetMapping("/readProjectInfo/{projectID}")
    public String readProjectInfo(@PathVariable int projectID, HttpSession session){
        this.project = service.readProjectInfo(projectID);
        session.setAttribute("project", this.project);
        return "show-project";
    }

    @GetMapping("/readProjectInfo/{subProjectID}/tasks")
    public String viewTasks(@PathVariable int subProjectID, Model model, HttpSession session){
        project = (Project) session.getAttribute("project");
        model.addAttribute("tasks", project.findSubProjectByID(subProjectID).getTasks());
        model.addAttribute("subProjectID", (Integer) subProjectID);
        return "show-tasks";
    }




    @GetMapping("/assignEmployee/{subProjectID}/{taskID}")
    public String assignEmployeeGet(@PathVariable int subProjectID,
                                    @PathVariable int taskID,
                                    Model model, HttpSession session){
        model.addAttribute("subProjectID", subProjectID);
        model.addAttribute("taskID", taskID);
        Assignment newAssignment = new Assignment();
        model.addAttribute("newAssignment", newAssignment);
        return "assign-employee";
    }

    @PostMapping("/assignEmployee/{subProjectID}/{taskID}")
    public String assignEmployeePost(@PathVariable int subProjectID,
                                     @PathVariable int taskID,
                                     @ModelAttribute Assignment assignment,
                                     Model model, HttpSession session){
        LocalDate subDeadline = project.findSubProjectById(subProjectID).getSubProjectDeadline();
        try {
            service.addAssignment(subDeadline, taskID, assignment);
        } catch (InvalidAssigmentException e) {
            model.addAttribute("newAssignment", assignment);
            model.addAttribute("errorMessage", e);
            return "assign-employee";
        }

        return "redirect:/readProjectInfo/" + project.getId();
    }

    @PostMapping("/deleteAssignment/{employeeID}/{taskID}")
    public String deleteAssignment(@PathVariable int employeeID,
                                   @PathVariable int taskID,
                                   Model model, HttpSession session){
        service.deleteAssignment(employeeID, taskID);
        return "redirect:/show-project";
    }

    @GetMapping("/addSubProject")
    public String createSubProject(Model model, HttpSession session) {
        if(isSessionInvalid(session)){
            return "redirect:/";
        }
        SubProject subProjectToAdd = new SubProject();
        model.addAttribute("subProject", subProjectToAdd);
        return "add-sub-project";
    }

    @PostMapping("/delete/subproject/{subProjectID}")
    public String deleteSubProject(@PathVariable int subProjectID, HttpSession session) {
        Project project = (Project) session.getAttribute("project");
        service.deleteSubProject(subProjectID);
        return "redirect:/readProjectInfo/" + project.getId();
    }

    @PostMapping("/addSubProject")
    public String addSubProject(@ModelAttribute SubProject subProjectToAdd, Model model) {
        try {
        int projectID = this.project.getId();
        service.addSubProject(subProjectToAdd, projectID);
        this.project.addSubProject(subProjectToAdd);
            return "redirect:/readProjectInfo/" + project.getId();
        } catch (InvalidDateException e) {
            model.addAttribute("subProject", subProjectToAdd);
            model.addAttribute("errorMessage", e.getMessage());
            return "add-sub-project";
        } catch (SQLException e) {
            return "error";
        }
    }

    @GetMapping("/projects/dashboard")
    public String viewProjectDashboard(Model model) {
        model.addAttribute("projects", service.allProjects());
        return "dashboard";
    }

    @GetMapping("/view/assignments/{subProjectID}/{taskID}")
    public String viewAssignmentPage(@PathVariable int subProjectID, @PathVariable int taskID, Model model, HttpSession session) {
        Project project = (Project) session.getAttribute("project");
        model.addAttribute("task", project.findTaskByID(subProjectID,taskID));
        return "view-assignment";
    }
}
