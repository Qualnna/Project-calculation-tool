package qualnna.dascalculator.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Assignment {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;
    private int timeSpent;
    private Employee assignedEmployee;

    public Assignment (){}
    public Assignment(LocalDate completionDate, int timeSpent, Employee assignedEmployee) {
        this.completionDate = completionDate;
        this.timeSpent = timeSpent;
        this.assignedEmployee = assignedEmployee;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}
