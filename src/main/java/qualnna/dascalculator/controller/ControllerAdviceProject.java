package qualnna.dascalculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import qualnna.dascalculator.exceptions.AssignmentNotFoundException;
import qualnna.dascalculator.exceptions.ProjectNotFoundException;

import java.sql.SQLException;

@ControllerAdvice
public class ControllerAdviceProject {


    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProjectNotFoundException(ProjectNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "404";
    }

    @ExceptionHandler(SQLException.class)
    public String handleDatabaseError(SQLException ex, Model model) {
        model.addAttribute("errorMessage", "Database error: could not complete transaction");
        System.out.println("SQL error: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occured");
        return "error";}

    @ExceptionHandler(AssignmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAssignmentNotFoundException(AssignmentNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "404";
    }
}
