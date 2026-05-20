package qualnna.dascalculator.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qualnna.dascalculator.exceptions.NoSubProjectFound;
import qualnna.dascalculator.exceptions.NoSuchEmployee;

import java.sql.SQLException;

@ControllerAdvice
public class ControllerAdviceProject {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error"; // your error view
    }

    @ExceptionHandler(NoSubProjectFound.class)
    public String handleNoSubProjectFound(NoSubProjectFound e, Model model) {
        model.addAttribute("errorMessage", "No sub project could be found with that");
        return "error"; // your error view
    }

    @ExceptionHandler(SQLException.class)
    public String handleDatabaseError(SQLException ex, Model model) {
        model.addAttribute("errorMessage", "Database error: could not complete transaction");
        System.out.println("SQL error: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoSuchEmployee.class)
    public String handleNoEmployeeToDelete(NoSuchEmployee ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }




    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occured");
        return "error";}
}

