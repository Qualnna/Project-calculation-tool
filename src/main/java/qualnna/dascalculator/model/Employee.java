package qualnna.dascalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int empId;
    private String name;
    private double hourlyRate;
    private List<String> skills = new ArrayList<>();
    private List<Task> assignedTask = new ArrayList<>();

    public Employee() {}

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Task> getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(List<Task> assignedTask) {
        this.assignedTask = assignedTask;
    }

    public void addSkills(List<String> newSkills) {
        for (String skill: newSkills) {
            skills.add(skill);
        }
    }
}
