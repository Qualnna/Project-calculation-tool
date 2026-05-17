package qualnna.dascalculator.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class SubProject {
    private String subProjectName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subProjectDeadline;
    private List<Task> tasks;

    public SubProject(){}

    public SubProject(String subProjectName, LocalDate subProjectDeadline, List<Task> tasks) {
        this.subProjectName = subProjectName;
        this.subProjectDeadline = subProjectDeadline;
        this.tasks = tasks;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public LocalDate getSubProjectDeadline() {
        return subProjectDeadline;
    }

    public void setSubProjectDeadline(LocalDate subProjectDeadline) {
        this.subProjectDeadline = subProjectDeadline;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
