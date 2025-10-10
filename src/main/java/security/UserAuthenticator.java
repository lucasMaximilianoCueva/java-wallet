package security;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/**
 * INTENTIONAL SECURITY VULNERABILITIES FOR DEMO PURPOSES
 * This class contains multiple security issues that SonarQube will detect:
 * - SQL Injection vulnerabilities
 * - Weak cryptography
 * - Hardcoded credentials
 * - Insecure random number generation
 */
public class UserAuthenticator {
    
    // SECURITY ISSUE: Hardcoded credentials
    private static final String DB_PASSWORD = "admin123";
    private static final String DB_USER = "root";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    
    // SECURITY ISSUE: Weak random number generator
    private Random random = new Random();
    
    /**
     * SECURITY ISSUE: SQL Injection vulnerability
     * This method is vulnerable to SQL injection attacks.
     */
    public boolean authenticateUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            // SQL INJECTION: String concatenation in SQL query
            String query = "SELECT * FROM users WHERE username = '" + username + 
                          "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            
            boolean authenticated = rs.next();
            rs.close();
            stmt.close();
            conn.close();
            
            return authenticated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * SECURITY ISSUE: Weak cryptographic hash (MD5)
     * MD5 is cryptographically broken and should not be used.
     */
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return password; // SECURITY ISSUE: Returning plain password on error
        }
    }
    
    /**
     * SECURITY ISSUE: Insecure random token generation
     * Using java.util.Random for security-sensitive operations.
     */
    public String generateSessionToken() {
        // SECURITY ISSUE: Predictable random numbers
        int token = random.nextInt(999999);
        return String.valueOf(token);
    }
    
    /**
     * SECURITY ISSUE: Another SQL injection vulnerability
     */
    public void updateUserEmail(String username, String newEmail) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            // SQL INJECTION: Direct string concatenation
            String query = "UPDATE users SET email = '" + newEmail + 
                          "' WHERE username = '" + username + "'";
            stmt.executeUpdate(query);
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
