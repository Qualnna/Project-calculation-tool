package qualnna.dascalculator.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class SubProject {
    private int subProjectID;
    private String subProjectName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subProjectDeadline;
    private List<Task> tasks;

    public SubProject(){}

    public SubProject(int subProjectID, String subProjectName, LocalDate subProjectDeadline) {
        this.subProjectID = subProjectID;
        this.subProjectName = subProjectName;
        this.subProjectDeadline = subProjectDeadline;
    }

    public SubProject(int subProjectID, String subProjectName, LocalDate subProjectDeadline, List<Task> tasks) {
        this.subProjectID = subProjectID;
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

    public int getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public Task findTaskByID(int taskID){
        for(Task task: tasks){
            if(task.getTaskID()==taskID){
                return task;
            }
        }
        return null;
    }
}
