package qualnna.dascalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int employeeID;
    private String employeeName;
    private float hourlyPayRate;
    private List<String> skills = new ArrayList<>();

    public Employee(){}

    public Employee(int employeeID, String employeeName, float hourlyPayRate) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.hourlyPayRate = hourlyPayRate;
    }

    public Employee(String employeeName, float hourlyPayRate, List<String> skills) {
        this.employeeName = employeeName;
        this.hourlyPayRate = hourlyPayRate;
        this.skills = skills;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public float getHourlyPayRate() {
        return hourlyPayRate;
    }

    public void setHourlyPayRate(float hourlyPayRate) {
        this.hourlyPayRate = hourlyPayRate;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getSkillsAsString() {
        if (skills == null || skills.isEmpty()) return null;
        return String.join(", ", skills);
    }

    public void addSkill(String skill){
        skills.add(skill);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", hourlyPayRate=" + hourlyPayRate +
                ", skills=" + skills +
                '}';
    }
}
