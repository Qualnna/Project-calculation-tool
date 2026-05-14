package qualnna.dascalculator.model;

public record Task(int taskId, int subId, String taskName, double workload) {
    @Override
    public String toString() {
        return "Task: " + taskName;
    }
}
