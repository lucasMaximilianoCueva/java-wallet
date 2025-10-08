import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test class for Main application
 */
public class MainTest {

    @Test
    public void testApplicationExists() {
        // Basic test to ensure Main class exists
        assertNotNull(Main.class);
    }

    @Test
    public void testTaskItemExists() {
        // Basic test to ensure TaskItem class exists
        assertNotNull(TaskItem.class);
    }
}
