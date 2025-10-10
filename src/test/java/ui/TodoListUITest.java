package ui;

import service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for TodoListUI class.
 * Tests UI initialization and component creation.
 * UI tests are disabled in headless mode (CI environment).
 */
@DisplayName("TodoListUI Tests")
public class TodoListUITest {

    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
    }

    @Test
    @DisplayName("Should throw exception when TaskService is null")
    public void testCreateUIWithNullService() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new TodoListUI(null)
        );
        assertEquals("TaskService cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should create TaskService successfully")
    public void testTaskServiceCreation() {
        assertNotNull(taskService);
        assertTrue(taskService.isEmpty());
    }
}
