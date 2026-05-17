package qualnna.dascalculator.model;

import java.util.List;

public class Task {
    private int taskID;
    private String taskName;
    private int workload;
    private List<ExternalResource> externalResources;
    private List<String> skills;
    private List<Assignment> assignments;

    public Task (){}

    public Task(int taskID, String taskName, int workload) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.workload = workload;
    }

    public Task(String taskName, int workload, List<ExternalResource> externalResources, List<String> skills, List<Assignment> assignments) {
        this.taskName = taskName;
        this.workload = workload;
        this.externalResources = externalResources;
        this.skills = skills;
        this.assignments = assignments;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public List<ExternalResource> getExternalResources() {
        return externalResources;
    }

    public void setExternalResources(List<ExternalResource> externalResources) {
        this.externalResources = externalResources;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
