package service;

import model.TaskItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for TaskService class.
 * Tests all service operations and business rules.
 */
@DisplayName("TaskService Tests")
public class TaskServiceTest {

    private TaskService taskService;
    private TaskItem task1;
    private TaskItem task2;
    private TaskItem task3;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
        task1 = new TaskItem("Task 1", "15-12-2025");
        task2 = new TaskItem("Task 2", "20-12-2025");
        task3 = new TaskItem("Overdue task", "01-01-2020");
    }

    @Test
    @DisplayName("Should start with empty task list")
    public void testInitialState() {
        assertTrue(taskService.isEmpty());
        assertEquals(0, taskService.getTaskCount());
    }

    @Test
    @DisplayName("Should add task successfully")
    public void testAddTask() {
        taskService.addTask(task1);
        assertEquals(1, taskService.getTaskCount());
        assertFalse(taskService.isEmpty());
    }

    @Test
    @DisplayName("Should add multiple tasks")
    public void testAddMultipleTasks() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.addTask(task3);
        assertEquals(3, taskService.getTaskCount());
    }

    @Test
    @DisplayName("Should throw exception when adding null task")
    public void testAddNullTask() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taskService.addTask(null)
        );
        assertEquals("Task cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should remove task at valid index")
    public void testRemoveTask() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.removeTask(0);
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    @DisplayName("Should throw exception when removing task at invalid index")
    public void testRemoveTaskInvalidIndex() {
        taskService.addTask(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskService.removeTask(5));
        assertThrows(IndexOutOfBoundsException.class, () -> taskService.removeTask(-1));
    }

    @Test
    @DisplayName("Should get task at valid index")
    public void testGetTask() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        TaskItem retrieved = taskService.getTask(1);
        assertEquals(task2, retrieved);
    }

    @Test
    @DisplayName("Should throw exception when getting task at invalid index")
    public void testGetTaskInvalidIndex() {
        taskService.addTask(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskService.getTask(5));
        assertThrows(IndexOutOfBoundsException.class, () -> taskService.getTask(-1));
    }

    @Test
    @DisplayName("Should get all tasks")
    public void testGetAllTasks() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        List<TaskItem> allTasks = taskService.getAllTasks();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    @DisplayName("Should return unmodifiable list")
    public void testGetAllTasksUnmodifiable() {
        taskService.addTask(task1);
        List<TaskItem> allTasks = taskService.getAllTasks();
        assertThrows(UnsupportedOperationException.class, () -> allTasks.add(task2));
    }

    @Test
    @DisplayName("Should get completed tasks only")
    public void testGetCompletedTasks() {
        task1.markAsCompleted();
        taskService.addTask(task1);
        taskService.addTask(task2);
        
        List<TaskItem> completedTasks = taskService.getCompletedTasks();
        assertEquals(1, completedTasks.size());
        assertTrue(completedTasks.contains(task1));
    }

    @Test
    @DisplayName("Should get pending tasks only")
    public void testGetPendingTasks() {
        task1.markAsCompleted();
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.addTask(task3);
        
        List<TaskItem> pendingTasks = taskService.getPendingTasks();
        assertEquals(2, pendingTasks.size());
        assertTrue(pendingTasks.contains(task2));
        assertTrue(pendingTasks.contains(task3));
    }

    @Test
    @DisplayName("Should get overdue tasks only")
    public void testGetOverdueTasks() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.addTask(task3);
        
        List<TaskItem> overdueTasks = taskService.getOverdueTasks();
        assertEquals(1, overdueTasks.size());
        assertTrue(overdueTasks.contains(task3));
    }

    @Test
    @DisplayName("Should not include completed tasks in overdue list")
    public void testOverdueExcludesCompleted() {
        task3.markAsCompleted();
        taskService.addTask(task3);
        
        List<TaskItem> overdueTasks = taskService.getOverdueTasks();
        assertEquals(0, overdueTasks.size());
    }

    @Test
    @DisplayName("Should clear all tasks")
    public void testClearAllTasks() {
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.clearAllTasks();
        
        assertTrue(taskService.isEmpty());
        assertEquals(0, taskService.getTaskCount());
    }

    @Test
    @DisplayName("Should handle clearing empty list")
    public void testClearEmptyList() {
        taskService.clearAllTasks();
        assertTrue(taskService.isEmpty());
    }

    @Test
    @DisplayName("Should return correct task count")
    public void testGetTaskCount() {
        assertEquals(0, taskService.getTaskCount());
        taskService.addTask(task1);
        assertEquals(1, taskService.getTaskCount());
        taskService.addTask(task2);
        assertEquals(2, taskService.getTaskCount());
        taskService.removeTask(0);
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    @DisplayName("Should correctly identify empty state")
    public void testIsEmpty() {
        assertTrue(taskService.isEmpty());
        taskService.addTask(task1);
        assertFalse(taskService.isEmpty());
        taskService.removeTask(0);
        assertTrue(taskService.isEmpty());
    }
}
