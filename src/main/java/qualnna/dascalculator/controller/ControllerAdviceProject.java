package qualnna.dascalculator.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ControllerAdviceProject {




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
}
