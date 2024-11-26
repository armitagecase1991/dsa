package com.dsa.maps.entity;

public class DependencyTask {
    private String taskId;
    private String dependencyTaskId;

    public DependencyTask(String taskId, String dependencyTaskId) {
        this.taskId = taskId;
        this.dependencyTaskId = dependencyTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDependencyTaskId() {
        return dependencyTaskId;
    }

    // You might also want to override equals() and hashCode() if you plan to use this in collections
}
