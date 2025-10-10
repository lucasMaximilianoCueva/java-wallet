package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for TaskItem class.
 * Tests all business logic, validations, and edge cases.
 */
@DisplayName("TaskItem Tests")
public class TaskItemTest {

    private TaskItem taskItem;

    @BeforeEach
    public void setUp() {
        taskItem = new TaskItem("Buy groceries", "15-12-2025");
    }

    @Test
    @DisplayName("Should create task with valid data")
    public void testCreateTaskWithValidData() {
        assertNotNull(taskItem);
        assertEquals("Buy groceries", taskItem.getTask());
        assertEquals("15-12-2025", taskItem.getDueDateFormatted());
        assertFalse(taskItem.isCompleted());
    }

    @Test
    @DisplayName("Should throw exception when task is null")
    public void testCreateTaskWithNullTask() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem(null, "15-12-2025")
        );
        assertEquals("Task cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when task is empty")
    public void testCreateTaskWithEmptyTask() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem("", "15-12-2025")
        );
        assertEquals("Task cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when task is only whitespace")
    public void testCreateTaskWithWhitespaceTask() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem("   ", "15-12-2025")
        );
        assertEquals("Task cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when due date is null")
    public void testCreateTaskWithNullDueDate() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem("Buy groceries", null)
        );
        assertEquals("Due date cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when due date is empty")
    public void testCreateTaskWithEmptyDueDate() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem("Buy groceries", "")
        );
        assertEquals("Due date cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when due date format is invalid")
    public void testCreateTaskWithInvalidDateFormat() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TaskItem("Buy groceries", "2025-12-15")
        );
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    @DisplayName("Should toggle task completion status")
    public void testToggleCompleted() {
        assertFalse(taskItem.isCompleted());
        taskItem.toggleCompleted();
        assertTrue(taskItem.isCompleted());
        taskItem.toggleCompleted();
        assertFalse(taskItem.isCompleted());
    }

    @Test
    @DisplayName("Should mark task as completed")
    public void testMarkAsCompleted() {
        assertFalse(taskItem.isCompleted());
        taskItem.markAsCompleted();
        assertTrue(taskItem.isCompleted());
    }

    @Test
    @DisplayName("Should mark task as not completed")
    public void testMarkAsNotCompleted() {
        taskItem.markAsCompleted();
        assertTrue(taskItem.isCompleted());
        taskItem.markAsNotCompleted();
        assertFalse(taskItem.isCompleted());
    }

    @Test
    @DisplayName("Should detect overdue tasks")
    public void testIsOverdue() {
        TaskItem overdueTask = new TaskItem("Old task", "01-01-2020");
        assertTrue(overdueTask.isOverdue());
    }

    @Test
    @DisplayName("Should not mark completed tasks as overdue")
    public void testCompletedTaskNotOverdue() {
        TaskItem overdueTask = new TaskItem("Old task", "01-01-2020");
        overdueTask.markAsCompleted();
        assertFalse(overdueTask.isOverdue());
    }

    @Test
    @DisplayName("Should not mark future tasks as overdue")
    public void testFutureTaskNotOverdue() {
        TaskItem futureTask = new TaskItem("Future task", "31-12-2030");
        assertFalse(futureTask.isOverdue());
    }

    @Test
    @DisplayName("Should update task description")
    public void testSetTask() {
        taskItem.setTask("Updated task");
        assertEquals("Updated task", taskItem.getTask());
    }

    @Test
    @DisplayName("Should trim whitespace when setting task")
    public void testSetTaskTrimsWhitespace() {
        taskItem.setTask("  Trimmed task  ");
        assertEquals("Trimmed task", taskItem.getTask());
    }

    @Test
    @DisplayName("Should throw exception when setting null task")
    public void testSetTaskWithNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taskItem.setTask(null)
        );
        assertEquals("Task cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when setting empty task")
    public void testSetTaskWithEmpty() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taskItem.setTask("")
        );
        assertEquals("Task cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should update due date")
    public void testSetDueDate() {
        taskItem.setDueDate("20-12-2025");
        assertEquals("20-12-2025", taskItem.getDueDateFormatted());
    }

    @Test
    @DisplayName("Should throw exception when setting invalid due date")
    public void testSetDueDateWithInvalidFormat() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taskItem.setDueDate("invalid-date")
        );
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    @DisplayName("Should format toString correctly for pending task")
    public void testToStringPending() {
        String result = taskItem.toString();
        assertTrue(result.contains("✗"));
        assertTrue(result.contains("Buy groceries"));
        assertTrue(result.contains("15-12-2025"));
    }

    @Test
    @DisplayName("Should format toString correctly for completed task")
    public void testToStringCompleted() {
        taskItem.markAsCompleted();
        String result = taskItem.toString();
        assertTrue(result.contains("✓"));
        assertTrue(result.contains("Buy groceries"));
    }

    @Test
    @DisplayName("Should format toString with overdue marker")
    public void testToStringOverdue() {
        TaskItem overdueTask = new TaskItem("Old task", "01-01-2020");
        String result = overdueTask.toString();
        assertTrue(result.contains("[OVERDUE]"));
    }

    @Test
    @DisplayName("Should implement equals correctly")
    public void testEquals() {
        TaskItem task1 = new TaskItem("Task", "15-12-2025");
        TaskItem task2 = new TaskItem("Task", "15-12-2025");
        TaskItem task3 = new TaskItem("Different", "15-12-2025");

        assertEquals(task1, task2);
        assertNotEquals(task1, task3);
        assertNotEquals(task1, null);
        assertEquals(task1, task1);
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    public void testHashCode() {
        TaskItem task1 = new TaskItem("Task", "15-12-2025");
        TaskItem task2 = new TaskItem("Task", "15-12-2025");

        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    @DisplayName("Should get due date as LocalDate")
    public void testGetDueDate() {
        LocalDate dueDate = taskItem.getDueDate();
        assertNotNull(dueDate);
        assertEquals(15, dueDate.getDayOfMonth());
        assertEquals(12, dueDate.getMonthValue());
        assertEquals(2025, dueDate.getYear());
    }
}
