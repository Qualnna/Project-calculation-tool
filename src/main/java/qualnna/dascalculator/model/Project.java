package qualnna.dascalculator.model;

import org.springframework.format.annotation.DateTimeFormat;
import qualnna.dascalculator.exceptions.NoSubProjectFound;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Project {
    private int id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private int totalRequiredWorkload;
    private int totalAssignedWorkload;
    private float estimatedPrice;
    private List<SubProject> subProjects;

    /*
    Fields containing all skills and employees may need to be added
     */

    public Project(){}

    public Project(int id, String name, LocalDate startdate, LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.deadline = deadline;
    }

    public Project(int id, String name, LocalDate startdate, LocalDate deadline, int totalRequiredWorkload, int totalAssignedWorkload, float estimatedPrice, List<SubProject> subProjects) {
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.deadline = deadline;
        this.totalRequiredWorkload = totalRequiredWorkload;
        this.totalAssignedWorkload = totalAssignedWorkload;
        this.estimatedPrice = estimatedPrice;
        this.subProjects = subProjects;
    }


    public void deleteTaskFromProject(Task task, int subProjectID) {
        SubProject project = findSubProjectByID(subProjectID);
        project.getTasks().remove(task);
    }

    public void addTaskToSubProject(Task task, int subID) {
        SubProject subProject = findSubProjectByID(subID);
        subProject.getTasks().add(task);
    }

    public SubProject findSubProjectByID(int subID) {
        for (SubProject sub : subProjects) {
            if (sub.getSubProjectID() == subID) {
                return sub;
            }
        }
        throw new NoSubProjectFound("Cant find a subproject with ID: " + subID + " in project.");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getTotalRequiredWorkload() {
        return totalRequiredWorkload;
    }

    public void setTotalRequiredWorkload(int totalRequiredWorkload) {
        this.totalRequiredWorkload = totalRequiredWorkload;
    }

    public int getTotalAssignedWorkload() {
        return totalAssignedWorkload;
    }

    public void setTotalAssignedWorkload(int totalAssignedWorkload) {
        this.totalAssignedWorkload = totalAssignedWorkload;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }

    public void addSubProject(SubProject subProject){
        subProjects.add(subProject);
    }

    public SubProject findSubProjectById(int subProjectID){
        for(SubProject subProject: subProjects){
            if(subProject.getSubProjectID() == subProjectID){
                return subProject;
            }
        }
        return null;
    }


    // Using chronounit from the java.time lib  to be able to calculate deadline
    public long daysLeftUntilDeadline() {
        return ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }


    // Completion rate of the assigned workload for the entire project
    public double getCompletionRateInHours() {
        if (getTotalRequiredWorkload() == 0) return 0.0;
        return (double) this.getTotalAssignedWorkload() / this.getTotalRequiredWorkload() * 100;
    }

    public double getTaskCompletionRate() {
        int completed = 0;
        int total = 0;
        for (SubProject sub : subProjects) {
            completed += sub.getCompletedTasks();
            total += sub.getTotalTasks();
        }
        if (total == 0) return 0;
        return (completed / (double) total) * 100;
    }

    public Task findTaskByID(int subProjectID, int taskID){
        return findSubProjectById(subProjectID).findTaskByID(taskID);
    }
}
