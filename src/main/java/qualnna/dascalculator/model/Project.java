package qualnna.dascalculator.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    public Task findTaskByID(int subProjectID, int taskID){
        return findSubProjectById(subProjectID).findTaskByID(taskID);
    }


}
