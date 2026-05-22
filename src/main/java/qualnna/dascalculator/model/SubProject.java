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

    public int getTotalTasks() {
       return tasks.size();
    }

    public int getCompletedTasks() {
        int completedTaskCount = 0;
        List<Task> tasks = getTasks();

        for (Task task : tasks) {
            int totalTimeSpentOnTask = 0;
            // Sum up all time spent by everyone assigned to this task
            for (Assignment assignment : task.getAssignments()) {
                totalTimeSpentOnTask += assignment.getTimeSpent();
            }
            // The task is only complete if the collective work matches the workload
            if (totalTimeSpentOnTask >= task.getWorkload()) {
                completedTaskCount++;
            }
        }
        return completedTaskCount;
    }


    public int getTotalRequiredWorkload() {
        int total = 0;
        for (Task task : tasks) {
            total += task.getWorkload();
        }
        return total;
    }

    public int getTotalAssignedWorkload() {
        int total = 0;
        for (Task task : tasks) {
            for (Assignment assignment : task.getAssignments()) {
                total += assignment.getTimeSpent();
            }
        }
        return total;
    }

    // Progress based on the amount of completed tasks out of the total amount of tasks associated
    // with the current subproject
    public double getProgressPercentage() {
        int total = getTotalTasks();
        if (total == 0) return 0;
        return (getCompletedTasks() / (double) total) * 100;
    }



    public Task findTaskById(int projectID,int taskID) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task with ID " + taskID + " not found in project " + projectID);
    }


    public String getTasksAsString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.getTaskName());
            sb.append(", ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
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

    public int subProjectEstimatedWorkload() {
        int sumOfWork = 0;
        for (Task task : tasks) {
            sumOfWork+= task.getWorkload();
        }
        return sumOfWork;
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
