package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a task item in the to-do list.
 * This class encapsulates task data and business logic.
 */
public class TaskItem {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    private String task;
    private LocalDate dueDate;
    private boolean isCompleted;

    /**
     * Creates a new task item.
     * 
     * @param task The task description
     * @param dueDateStr The due date in format dd-MM-yyyy
     * @throws IllegalArgumentException if task is null/empty or date format is invalid
     */
    public TaskItem(String task, String dueDateStr) {
        if (task == null || task.trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }
        
        this.task = task.trim();
        this.dueDate = parseDate(dueDateStr);
        this.isCompleted = false;
    }

    /**
     * Parses a date string to LocalDate.
     * 
     * @param dateStr The date string in format dd-MM-yyyy
     * @return The parsed LocalDate
     * @throws IllegalArgumentException if date format is invalid
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Due date cannot be null or empty");
        }
        
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected: dd-MM-yyyy", e);
        }
    }

    /**
     * Toggles the completion status of the task.
     */
    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsCompleted() {
        isCompleted = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotCompleted() {
        isCompleted = false;
    }

    /**
     * Checks if the task is overdue.
     * 
     * @return true if the task is overdue and not completed
     */
    public boolean isOverdue() {
        return !isCompleted && dueDate.isBefore(LocalDate.now());
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        if (task == null || task.trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }
        this.task = task.trim();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDueDateFormatted() {
        return dueDate.format(DATE_FORMATTER);
    }

    public void setDueDate(String dueDateStr) {
        this.dueDate = parseDate(dueDateStr);
    }

    @Override
    public String toString() {
        String status = isCompleted ? "✓" : "✗";
        String overdueMarker = isOverdue() ? " [OVERDUE]" : "";
        return String.format("%s %s (%s)%s", status, task, getDueDateFormatted(), overdueMarker);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskItem taskItem = (TaskItem) o;
        return Objects.equals(task, taskItem.task) && 
               Objects.equals(dueDate, taskItem.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, dueDate);
    }
}
