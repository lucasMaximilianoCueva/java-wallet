package service;

import model.TaskItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing tasks.
 * Implements business logic for task operations following SOLID principles.
 */
public class TaskService {
    private final List<TaskItem> tasks;

    public TaskService() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to the list.
     * 
     * @param task The task to add
     * @throws IllegalArgumentException if task is null
     */
    public void addTask(TaskItem task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }

    /**
     * Removes a task at the specified index.
     * 
     * @param index The index of the task to remove
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        tasks.remove(index);
    }

    /**
     * Gets a task at the specified index.
     * 
     * @param index The index of the task
     * @return The task at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public TaskItem getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.get(index);
    }

    /**
     * Gets all tasks.
     * 
     * @return An unmodifiable list of all tasks
     */
    public List<TaskItem> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Gets all completed tasks.
     * 
     * @return List of completed tasks
     */
    public List<TaskItem> getCompletedTasks() {
        return tasks.stream()
                .filter(TaskItem::isCompleted)
                .collect(Collectors.toList());
    }

    /**
     * Gets all pending tasks.
     * 
     * @return List of pending tasks
     */
    public List<TaskItem> getPendingTasks() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    /**
     * Gets all overdue tasks.
     * 
     * @return List of overdue tasks
     */
    public List<TaskItem> getOverdueTasks() {
        return tasks.stream()
                .filter(TaskItem::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Gets the total number of tasks.
     * 
     * @return The total number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Clears all tasks.
     */
    public void clearAllTasks() {
        tasks.clear();
    }

    /**
     * Checks if the task list is empty.
     * 
     * @return true if there are no tasks
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
