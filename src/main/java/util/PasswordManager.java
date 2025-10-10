package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * INTENTIONAL SECURITY HOTSPOTS FOR DEMO PURPOSES
 * This class contains critical security issues.
 */
public class PasswordManager {
    
    // SECURITY HOTSPOT: Hardcoded encryption key
    private static final String SECRET_KEY = "MySecretKey12345";
    
    // SECURITY HOTSPOT: Weak encryption algorithm (DES)
    private static final String ALGORITHM = "DES";
    
    /**
     * SECURITY HOTSPOT: Weak encryption with hardcoded key
     * SECURITY ISSUE: Using deprecated/weak DES algorithm
     */
    public String encryptPassword(String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            // RELIABILITY ISSUE: Returning sensitive data on error
            return password;
        }
    }
    
    /**
     * SECURITY HOTSPOT: Weak decryption
     */
    public String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decrypted);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * SECURITY ISSUE: Password stored in plain text
     * RELIABILITY ISSUE: No validation
     */
    public void savePasswordToFile(String username, String password) {
        try {
            // SECURITY ISSUE: Storing passwords in plain text
            java.io.FileWriter writer = new java.io.FileWriter("passwords.txt", true);
            writer.write(username + ":" + password + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * SECURITY HOTSPOT: Weak password validation
     * RELIABILITY ISSUE: Incomplete checks
     */
    public boolean isPasswordStrong(String password) {
        // RELIABILITY ISSUE: Too simple validation
        if (password == null || password.length() < 6) {
            return false;
        }
        return true; // SECURITY ISSUE: No complexity requirements
    }
    
    /**
     * SECURITY ISSUE: Logging sensitive information
     */
    public void logPasswordAttempt(String username, String password) {
        // SECURITY ISSUE: Logging passwords
        System.out.println("Login attempt - User: " + username + ", Password: " + password);
    }
}
