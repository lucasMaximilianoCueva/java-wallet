package util;

import java.util.regex.Pattern;

/**
 * INTENTIONAL CODE DUPLICATION AND RELIABILITY ISSUES FOR DEMO
 * This class contains duplicated code and poor practices.
 */
public class DataValidator {
    
    /**
     * DUPLICATION: This method has duplicated validation logic
     * RELIABILITY ISSUE: Incomplete validation
     */
    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (email.length() < 5) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!email.contains(".")) {
            return false;
        }
        return true;
    }
    
    /**
     * DUPLICATION: Similar validation logic as validateEmail
     * RELIABILITY ISSUE: Incomplete validation
     */
    public boolean validateUsername(String username) {
        if (username == null) {
            return false;
        }
        if (username.isEmpty()) {
            return false;
        }
        if (username.length() < 3) {
            return false;
        }
        if (username.length() > 20) {
            return false;
        }
        return true;
    }
    
    /**
     * DUPLICATION: More duplicated validation logic
     * RELIABILITY ISSUE: Magic numbers
     */
    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        if (password.length() > 50) {
            return false;
        }
        return true;
    }
    
    /**
     * DUPLICATION: Repeated null/empty checks
     */
    public boolean validatePhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        if (phone.isEmpty()) {
            return false;
        }
        if (phone.length() < 10) {
            return false;
        }
        if (phone.length() > 15) {
            return false;
        }
        return true;
    }
    
    /**
     * RELIABILITY ISSUE: Complex method with high cyclomatic complexity
     * MAINTAINABILITY ISSUE: Too many nested conditions
     */
    public String validateUserInput(String input, String type) {
        if (input != null) {
            if (!input.isEmpty()) {
                if (type.equals("email")) {
                    if (input.contains("@")) {
                        if (input.contains(".")) {
                            if (input.length() > 5) {
                                return "valid";
                            } else {
                                return "too short";
                            }
                        } else {
                            return "missing dot";
                        }
                    } else {
                        return "missing @";
                    }
                } else if (type.equals("phone")) {
                    if (input.length() >= 10) {
                        if (input.matches("[0-9]+")) {
                            return "valid";
                        } else {
                            return "invalid characters";
                        }
                    } else {
                        return "too short";
                    }
                } else {
                    return "unknown type";
                }
            } else {
                return "empty";
            }
        } else {
            return "null";
        }
    }
    
    /**
     * DUPLICATION: Copy-paste code with minor variations
     */
    public boolean isValidEmailFormat(String email) {
        if (email == null) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (email.length() < 5) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!email.contains(".")) {
            return false;
        }
        // Additional check
        if (email.startsWith("@") || email.endsWith("@")) {
            return false;
        }
        return true;
    }
}
