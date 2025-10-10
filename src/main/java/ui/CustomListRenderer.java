package ui;

import model.TaskItem;

import javax.swing.*;
import java.awt.*;

/**
 * Custom list renderer for displaying task items with visual indicators.
 * Provides better UI/UX for the to-do list.
 */
public class CustomListRenderer extends DefaultListCellRenderer {
    private static final Color COMPLETED_COLOR = new Color(200, 255, 200);
    private static final Color OVERDUE_COLOR = new Color(255, 200, 200);
    private static final Color NORMAL_COLOR = Color.WHITE;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                  int index, boolean isSelected, 
                                                  boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus
        );
        
        if (value instanceof TaskItem) {
            TaskItem task = (TaskItem) value;
            
            if (!isSelected) {
                if (task.isCompleted()) {
                    label.setBackground(COMPLETED_COLOR);
                } else if (task.isOverdue()) {
                    label.setBackground(OVERDUE_COLOR);
                } else {
                    label.setBackground(NORMAL_COLOR);
                }
            }
            
            // Strike through completed tasks
            if (task.isCompleted()) {
                label.setFont(label.getFont().deriveFont(Font.ITALIC));
            }
        }
        
        return label;
    }
}
