package com.dsa.maps.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskManagement {
    private Map<String, Boolean> tasks;
    private Map<String, Map<String, Object>> taskAttributes;
    private Map<String, Set<String>> dependencies;
    private Map<String, Set<String>> dependents;


    public TaskManagement(){
        this.tasks = new HashMap<>();
        this.taskAttributes = new HashMap<>();
        this.dependencies = new HashMap<>();
        this.dependents = new HashMap<>();
    }

    
    public boolean addTask(String taskId, String title, String description, String status, int priority) {
        if (tasks.containsKey(taskId)) {
            return false;
        }
        tasks.put(taskId, true);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", title);
        attributes.put("description", description);
        attributes.put("status", status);
        attributes.put("priority", priority);
        taskAttributes.put(taskId, attributes);
        return true;
    }

    public boolean updateTask(String taskId, String title, String description, String status, Integer priority) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Map<String, Object> attributes = taskAttributes.get(taskId);
        if (title != null) {
            attributes.put("title", title);
        }
        if (description != null) {
            attributes.put("description", description);
        }
        if (status != null) {
            attributes.put("status", status);
            if ("Closed".equals(status)) {
                for (String depId : dependents.getOrDefault(taskId, new HashSet<>())) {
                    if (areDependenciesClosed(depId)) {
                        taskAttributes.get(depId).put("status", "Open");
                    }
                }
            }
        }
        if (priority != null) {
            attributes.put("priority", priority);
        }
        return true;
    }
    
    private boolean areDependenciesClosed(String taskId) {
        for (String depId : dependencies.getOrDefault(taskId, new HashSet<>())) {
            if (!"Closed".equals(taskAttributes.get(depId).get("status"))) {
                return false;
            }
        }
        return true;
    }

    public boolean removeTask(String taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            taskAttributes.remove(taskId);
    
            for (String depTaskId : dependencies.getOrDefault(taskId, new HashSet<>())) {
                dependents.get(depTaskId).remove(taskId);
            }
            for (String depTaskId : dependents.getOrDefault(taskId, new HashSet<>())) {
                dependencies.get(depTaskId).remove(taskId);
            }
            dependencies.remove(taskId);
            dependents.remove(taskId);
    
            return true;
        }
        return false;
    }

    public List<String> getTasksByStatus(String status) {
        List<String> taskIds = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : taskAttributes.entrySet()) {
            if (status.equals(entry.getValue().get("status"))) {
                taskIds.add(entry.getKey());
            }
        }
        Collections.sort(taskIds);
        return taskIds;
    }

    public List<String> getTasksByPriority(int priority) {
        List<String> taskIds = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : taskAttributes.entrySet()) {
            if (priority == (int) entry.getValue().get("priority")) {
                taskIds.add(entry.getKey());
            }
        }
        Collections.sort(taskIds);
        return taskIds;
    }

    boolean addDependency(String taskId, String dependencyTaskId){
        if (!tasks.containsKey(taskId) || !tasks.containsKey(dependencyTaskId)) {
            return false;
        }

        if (isCircularDependency(taskId, dependencyTaskId)) {
            return false;
        }   
        
        dependencies.computeIfAbsent(taskId, k -> new HashSet<>()).add(dependencyTaskId);
        dependents.computeIfAbsent(dependencyTaskId, k -> new HashSet<>()).add(taskId);

        return true;
                
}
        
    private boolean isCircularDependency(String taskId, String dependencyTaskId) {
        Set<String> visited = new HashSet<>();
        return hasCycle(dependencyTaskId, taskId, visited);

    }

    private boolean hasCycle(String currentTask, String targetTask, Set<String> visited) {
        if (currentTask.equals(targetTask)) {
            return true; 
        }
        if (visited.contains(currentTask)) {
            return false; 
        }
        visited.add(currentTask);
        for (String dep : dependencies.getOrDefault(currentTask, new HashSet<>())) {
            if (hasCycle(dep, targetTask, visited)) {
                return true;
            }
        }
        visited.remove(currentTask);
        return false;
    }

    public boolean removeDependency(String taskId, String dependencyTaskId){
        if (dependencies.getOrDefault(taskId, Collections.emptySet()).remove(dependencyTaskId)) {
            dependents.getOrDefault(dependencyTaskId, Collections.emptySet()).remove(taskId);
            return true;
        }
        return false;
    }

    public List<String> getDependentTasks(String taskId) {
        Set<String> dependentSet = dependents.getOrDefault(taskId, Collections.emptySet());
        return new ArrayList<>(dependentSet);
    }
}
