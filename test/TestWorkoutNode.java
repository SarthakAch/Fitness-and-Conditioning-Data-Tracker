import models.WorkoutNode;
import java.time.LocalDate;

/**
 * TestWorkoutNode - Tests all getters and setters for WorkoutNode
 * Author: Rami Fayad
 */
public class TestWorkoutNode {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  TestWorkoutNode - Rami Fayad");
        System.out.println("========================================\n");

        testConstructorAndGetters();
        testSetters();
        testToString();
        testMultipleNodes();

        System.out.println("\n========================================");
        System.out.println("  Results: " + passed + " passed, " + failed + " failed");
        System.out.println("========================================");
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            failed++;
        }
    }

    // ── Test Groups ───────────────────────────────────────────────────────────

    static void testConstructorAndGetters() {
        System.out.println("--- Constructor & Getters ---");

        LocalDate date = LocalDate.of(2024, 6, 15);
        WorkoutNode node = new WorkoutNode(date, 1500.0, 45, 75.5);

        assertTrue("getDate() returns correct date",
                node.getDate().equals(LocalDate.of(2024, 6, 15)));

        assertTrue("getLiftingVolume() returns 1500.0",
                node.getLiftingVolume() == 1500.0);

        assertTrue("getCardioDuration() returns 45",
                node.getCardioDuration() == 45);

        assertTrue("getBodyWeight() returns 75.5",
                node.getBodyWeight() == 75.5);

        // Edge case: zero values
        WorkoutNode zeroNode = new WorkoutNode(LocalDate.of(2024, 1, 1), 0.0, 0, 0.0);
        assertTrue("getLiftingVolume() handles 0.0",
                zeroNode.getLiftingVolume() == 0.0);
        assertTrue("getCardioDuration() handles 0",
                zeroNode.getCardioDuration() == 0);
        assertTrue("getBodyWeight() handles 0.0",
                zeroNode.getBodyWeight() == 0.0);
    }

    static void testSetters() {
        System.out.println("\n--- Setters ---");

        LocalDate date = LocalDate.of(2024, 3, 10);
        WorkoutNode node = new WorkoutNode(date, 1000.0, 30, 80.0);

        // setLiftingVolume
        node.setLiftingVolume(2500.75);
        assertTrue("setLiftingVolume() updates value",
                node.getLiftingVolume() == 2500.75);

        // setCardioDuration
        node.setCardioDuration(60);
        assertTrue("setCardioDuration() updates value",
                node.getCardioDuration() == 60);

        // setBodyWeight
        node.setBodyWeight(78.3);
        assertTrue("setBodyWeight() updates value",
                node.getBodyWeight() == 78.3);

        // Date should remain unchanged (no date setter by design)
        assertTrue("date unchanged after setters",
                node.getDate().equals(LocalDate.of(2024, 3, 10)));

        // Overwrite with new values
        node.setLiftingVolume(0.0);
        assertTrue("setLiftingVolume() can be set to 0.0",
                node.getLiftingVolume() == 0.0);

        node.setCardioDuration(0);
        assertTrue("setCardioDuration() can be set to 0",
                node.getCardioDuration() == 0);
    }

    static void testToString() {
        System.out.println("\n--- toString() ---");

        WorkoutNode node = new WorkoutNode(LocalDate.of(2024, 9, 1), 1200.0, 30, 70.0);
        String result = node.toString();

        assertTrue("toString() contains date",
                result.contains("2024-09-01"));
        assertTrue("toString() contains lifting volume",
                result.contains("1200.00"));
        assertTrue("toString() contains cardio duration",
                result.contains("30"));
        assertTrue("toString() contains body weight",
                result.contains("70.00"));
    }

    static void testMultipleNodes() {
        System.out.println("\n--- Multiple Nodes (independence check) ---");

        WorkoutNode a = new WorkoutNode(LocalDate.of(2024, 1, 1), 500.0, 20, 65.0);
        WorkoutNode b = new WorkoutNode(LocalDate.of(2024, 2, 1), 800.0, 40, 70.0);

        // Mutate b, a should be unaffected
        b.setLiftingVolume(9999.0);
        assertTrue("Nodes are independent (a.liftingVolume unchanged)",
                a.getLiftingVolume() == 500.0);

        assertTrue("Different dates are stored correctly",
                !a.getDate().equals(b.getDate()));
    }
}
