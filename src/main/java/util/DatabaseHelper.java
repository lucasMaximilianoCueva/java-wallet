package util;

import java.sql.*;

/**
 * INTENTIONAL RELIABILITY AND SECURITY ISSUES FOR DEMO
 * Multiple database-related vulnerabilities and poor practices.
 */
public class DatabaseHelper {
    
    // SECURITY ISSUE: Hardcoded credentials
    private String dbUrl = "jdbc:mysql://localhost:3306/todolist";
    private String dbUser = "admin";
    private String dbPassword = "password123";
    
    /**
     * RELIABILITY ISSUE: Connection not closed properly
     * SECURITY ISSUE: SQL Injection
     */
    public ResultSet executeQuery(String tableName, String condition) throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement stmt = conn.createStatement();
        
        // SQL INJECTION: Building query with string concatenation
        String query = "SELECT * FROM " + tableName + " WHERE " + condition;
        ResultSet rs = stmt.executeQuery(query);
        
        // RELIABILITY ISSUE: Connection and statement not closed
        return rs;
    }
    
    /**
     * RELIABILITY ISSUE: Multiple resource leaks
     */
    public void insertTask(String task, String dueDate) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            stmt = conn.createStatement();
            
            // SQL INJECTION: String concatenation
            String sql = "INSERT INTO tasks (task, due_date) VALUES ('" + 
                        task + "', '" + dueDate + "')";
            stmt.executeUpdate(sql);
            
            // RELIABILITY ISSUE: Resources not closed in finally block
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * DUPLICATION: Similar code to insertTask
     * RELIABILITY ISSUE: Resource leak
     */
    public void updateTask(String taskId, String newTask) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            stmt = conn.createStatement();
            
            // SQL INJECTION: String concatenation
            String sql = "UPDATE tasks SET task = '" + newTask + 
                        "' WHERE id = " + taskId;
            stmt.executeUpdate(sql);
            
            // RELIABILITY ISSUE: Resources not closed
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * DUPLICATION: Similar code pattern repeated
     * RELIABILITY ISSUE: Resource leak
     */
    public void deleteTask(String taskId) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            stmt = conn.createStatement();
            
            // SQL INJECTION: String concatenation
            String sql = "DELETE FROM tasks WHERE id = " + taskId;
            stmt.executeUpdate(sql);
            
            // RELIABILITY ISSUE: Resources not closed
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * RELIABILITY ISSUE: Null pointer risk
     * MAINTAINABILITY ISSUE: Complex nested conditions
     */
    public boolean checkTaskExists(String taskId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (conn != null) {
                stmt = conn.createStatement();
                if (stmt != null) {
                    rs = stmt.executeQuery("SELECT * FROM tasks WHERE id = " + taskId);
                    if (rs != null) {
                        if (rs.next()) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // RELIABILITY ISSUE: Resources not closed
        return false;
    }
}
