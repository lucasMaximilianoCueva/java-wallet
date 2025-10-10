package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * INTENTIONAL RELIABILITY AND SECURITY ISSUES FOR DEMO PURPOSES
 * This class contains multiple issues that SonarQube will detect:
 * - Resource leaks
 * - Path traversal vulnerabilities
 * - Exception handling issues
 */
public class FileManager {
    
    /**
     * RELIABILITY ISSUE: Resource leak - FileInputStream not closed
     * SECURITY ISSUE: Path traversal vulnerability
     */
    public String readFile(String filename) {
        try {
            // SECURITY ISSUE: No path validation, allows path traversal
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            // RELIABILITY ISSUE: Resources not closed (no try-with-resources)
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * SECURITY ISSUE: Path traversal vulnerability
     * RELIABILITY ISSUE: Poor exception handling
     */
    public void writeFile(String filename, String content) {
        try {
            // SECURITY ISSUE: User-controlled file path without validation
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(content.getBytes());
            // RELIABILITY ISSUE: Stream not closed
        } catch (Exception e) {
            // RELIABILITY ISSUE: Catching generic Exception
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * SECURITY ISSUE: Command injection vulnerability
     * RELIABILITY ISSUE: Process not properly managed
     */
    public void executeCommand(String userInput) {
        try {
            // SECURITY ISSUE: Executing user input directly
            Runtime.getRuntime().exec(userInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * RELIABILITY ISSUE: Multiple resource leaks
     */
    public void copyFile(String source, String dest) {
        FileInputStream in = null;
        FileOutputStream out = null;
        
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            // RELIABILITY ISSUE: Streams not closed in finally block
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * SECURITY ISSUE: Deserialization vulnerability
     */
    public Object deserializeObject(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // SECURITY ISSUE: Deserializing untrusted data
            Object obj = in.readObject();
            in.close();
            fileIn.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
