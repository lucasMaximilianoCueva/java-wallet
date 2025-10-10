import service.TaskService;
import ui.TodoListUI;

import javax.swing.*;

/**
 * Main entry point for the To-Do List application.
 * This application demonstrates clean architecture and SOLID principles.
 * 
 * @author Lucas Maximiliano Cueva
 * @version 1.0
 */
public class Main {
    
    /**
     * Application entry point.
     * Initializes the application on the Event Dispatch Thread.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskService taskService = new TaskService();
            TodoListUI ui = new TodoListUI(taskService);
            ui.show();
        });
    }
}
