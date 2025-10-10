package ui;

import model.TaskItem;
import service.TaskService;

import javax.swing.*;
import java.awt.*;

/**
 * Main UI class for the To-Do List application.
 * Handles all user interface components and interactions.
 */
public class TodoListUI {
    private final TaskService taskService;
    private DefaultListModel<TaskItem> listModel;
    private JList<TaskItem> taskList;
    private JTextField taskField;
    private JTextField dateField;
    private JFrame frame;

    public TodoListUI(TaskService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("TaskService cannot be null");
        }
        this.taskService = taskService;
        initializeUI();
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        frame = new JFrame("To-Do List Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Task list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new CustomListRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    /**
     * Creates the input panel for task entry.
     * 
     * @return The configured input panel
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Add New Task"));

        panel.add(new JLabel("Task:"));
        taskField = new JTextField();
        panel.add(taskField);

        panel.add(new JLabel("Due Date (dd-MM-yyyy):"));
        dateField = new JTextField();
        panel.add(dateField);

        return panel;
    }

    /**
     * Creates the button panel with action buttons.
     * 
     * @return The configured button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton editButton = new JButton("Edit");
        JButton toggleButton = new JButton("Toggle Status");
        JButton clearButton = new JButton("Clear All");

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        editButton.addActionListener(e -> editTask());
        toggleButton.addActionListener(e -> toggleTaskStatus());
        clearButton.addActionListener(e -> clearAllTasks());

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(editButton);
        panel.add(toggleButton);
        panel.add(clearButton);

        return panel;
    }

    /**
     * Adds a new task to the list.
     */
    private void addTask() {
        String taskText = taskField.getText().trim();
        String dueDateText = dateField.getText().trim();
        
        if (taskText.isEmpty() || dueDateText.isEmpty()) {
            showError("Please enter both task and due date");
            return;
        }

        try {
            TaskItem newTask = new TaskItem(taskText, dueDateText);
            taskService.addTask(newTask);
            listModel.addElement(newTask);
            taskField.setText("");
            dateField.setText("");
            showInfo("Task added successfully!");
        } catch (IllegalArgumentException e) {
            showError("Error adding task: " + e.getMessage());
        }
    }

    /**
     * Removes the selected task.
     */
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            showError("Please select a task to remove");
            return;
        }

        try {
            taskService.removeTask(selectedIndex);
            listModel.remove(selectedIndex);
            showInfo("Task removed successfully!");
        } catch (IndexOutOfBoundsException e) {
            showError("Error removing task: " + e.getMessage());
        }
    }

    /**
     * Edits the selected task.
     */
    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            showError("Please select a task to edit");
            return;
        }

        try {
            TaskItem item = taskService.getTask(selectedIndex);
            String newTaskText = JOptionPane.showInputDialog(
                frame, "Enter new task:", item.getTask()
            );
            String newDueDateText = JOptionPane.showInputDialog(
                frame, "Enter new due date (dd-MM-yyyy):", item.getDueDateFormatted()
            );

            if (newTaskText != null && newDueDateText != null) {
                item.setTask(newTaskText);
                item.setDueDate(newDueDateText);
                taskList.repaint();
                showInfo("Task updated successfully!");
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            showError("Error editing task: " + e.getMessage());
        }
    }

    /**
     * Toggles the completion status of the selected task.
     */
    private void toggleTaskStatus() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            showError("Please select a task to toggle");
            return;
        }

        try {
            TaskItem item = taskService.getTask(selectedIndex);
            item.toggleCompleted();
            taskList.repaint();
        } catch (IndexOutOfBoundsException e) {
            showError("Error toggling task: " + e.getMessage());
        }
    }

    /**
     * Clears all tasks after confirmation.
     */
    private void clearAllTasks() {
        if (taskService.isEmpty()) {
            showInfo("No tasks to clear");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to clear all tasks?",
            "Confirm Clear",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            taskService.clearAllTasks();
            listModel.clear();
            showInfo("All tasks cleared!");
        }
    }

    /**
     * Shows an error message dialog.
     * 
     * @param message The error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an information message dialog.
     * 
     * @param message The information message
     */
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Makes the UI visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Gets the frame for testing purposes.
     * 
     * @return The main JFrame
     */
    JFrame getFrame() {
        return frame;
    }
}
