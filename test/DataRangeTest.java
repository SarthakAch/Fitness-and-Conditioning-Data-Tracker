import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

public class DateRangeTest {
    @Test
    public void testVariousDateRanges() {
        AVLTree tree = new AVLTree();
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate middle = LocalDate.of(2024, 1, 15);
        LocalDate end = LocalDate.of(2024, 2, 1);

        tree.insert(new WorkoutNode(1, "New Year", start));
        tree.insert(new WorkoutNode(2, "Mid-Month", middle));
        tree.insert(new WorkoutNode(3, "Next Month", end));

        // Query only for January
        List<WorkoutNode> results = tree.getWorkoutsInRange(start, middle);
        
        assertEquals("Should have found 2 workouts", 2, results.size());
        // Verify the February workout is NOT in the list
        for (WorkoutNode node : results) {
            assertFalse("Found a workout outside the range!", node.getDate().equals(end));
        }
    }
}
