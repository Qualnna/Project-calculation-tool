package qualnna.dascalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.SQLException;

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

}
