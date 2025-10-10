package util;

/**
 * INTENTIONAL CODE DUPLICATION FOR DEMO PURPOSES
 * This class contains heavily duplicated code.
 */
public class StringUtils {
    
    /**
     * DUPLICATION: Repeated validation logic
     */
    public boolean isValidString(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        if (str.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * DUPLICATION: Almost identical to isValidString
     */
    public boolean isNotEmpty(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        if (str.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * DUPLICATION: Same logic repeated again
     */
    public boolean hasContent(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        if (str.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * RELIABILITY ISSUE: Null pointer exception risk
     */
    public String capitalize(String str) {
        // RELIABILITY ISSUE: No null check
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * DUPLICATION: Repeated string manipulation pattern
     */
    public String formatName(String name) {
        if (name == null) {
            return "";
        }
        if (name.isEmpty()) {
            return "";
        }
        if (name.trim().isEmpty()) {
            return "";
        }
        return name.trim().toUpperCase();
    }
    
    /**
     * DUPLICATION: Similar to formatName
     */
    public String formatEmail(String email) {
        if (email == null) {
            return "";
        }
        if (email.isEmpty()) {
            return "";
        }
        if (email.trim().isEmpty()) {
            return "";
        }
        return email.trim().toLowerCase();
    }
    
    /**
     * RELIABILITY ISSUE: Potential ArrayIndexOutOfBoundsException
     * MAINTAINABILITY ISSUE: Complex logic
     */
    public String getFirstWord(String sentence) {
        String[] words = sentence.split(" ");
        // RELIABILITY ISSUE: No bounds checking
        return words[0];
    }
    
    /**
     * RELIABILITY ISSUE: Infinite loop risk
     */
    public int countOccurrences(String text, String pattern) {
        int count = 0;
        int index = 0;
        
        // RELIABILITY ISSUE: Potential infinite loop if pattern is empty
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index++;
        }
        
        return count;
    }
}
