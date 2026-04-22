import models.WorkoutNode;
import structures.SinglyLinkedList;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * TestSinglyLinkedList - Inserts 10 dates in random order and verifies
 *                        the list maintains chronological (ascending) order.
 * Author: Rami Fayad
 */
public class TestSinglyLinkedList {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  TestSinglyLinkedList - Rami Fayad");
        System.out.println("========================================\n");

        testChronologicalOrder();
        testSize();
        testSearch();
        testRangeQuery();
        testEdgeCases();

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

    /** Build a WorkoutNode with dummy fitness values for a given date. */
    static WorkoutNode node(LocalDate date) {
        return new WorkoutNode(date, 1000.0, 30, 75.0);
    }

    // ── Test Groups ───────────────────────────────────────────────────────────

    static void testChronologicalOrder() {
        System.out.println("--- Chronological Order (10 random-order inserts) ---");

        SinglyLinkedList list = new SinglyLinkedList();

        // 10 dates inserted in deliberately scrambled order
        List<LocalDate> insertOrder = Arrays.asList(
            LocalDate.of(2024, 5, 15),
            LocalDate.of(2024, 1, 3),
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 3, 8),
            LocalDate.of(2024, 7, 20),
            LocalDate.of(2024, 2, 14),
            LocalDate.of(2024, 10, 1),
            LocalDate.of(2024, 6, 30),
            LocalDate.of(2024, 9, 9),
            LocalDate.of(2024, 4, 22)
        );

        System.out.println("  Inserting dates in this order:");
        for (LocalDate d : insertOrder) {
            System.out.println("    -> " + d);
            list.insert(node(d));
        }

        // Expected sorted order
        LocalDate[] expected = {
            LocalDate.of(2024, 1, 3),
            LocalDate.of(2024, 2, 14),
            LocalDate.of(2024, 3, 8),
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 5, 15),
            LocalDate.of(2024, 6, 30),
            LocalDate.of(2024, 7, 20),
            LocalDate.of(2024, 9, 9),
            LocalDate.of(2024, 10, 1),
            LocalDate.of(2024, 12, 25)
        };

        // Verify via range query covering all dates
        List<WorkoutNode> all = list.rangeQuery(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2025, 1, 1)
        );

        System.out.println("\n  List order after all inserts:");
        for (WorkoutNode w : all) {
            System.out.println("    " + w.getDate());
        }

        assertTrue("List contains all 10 entries", all.size() == 10);

        boolean ordered = true;
        for (int i = 0; i < expected.length; i++) {
            if (!all.get(i).getDate().equals(expected[i])) {
                ordered = false;
                System.out.println("  Mismatch at index " + i +
                    ": expected " + expected[i] + ", got " + all.get(i).getDate());
            }
        }
        assertTrue("All 10 dates are in chronological order", ordered);
    }

    static void testSize() {
        System.out.println("\n--- Size ---");

        SinglyLinkedList list = new SinglyLinkedList();
        assertTrue("Empty list has size 0", list.getSize() == 0);

        list.insert(node(LocalDate.of(2024, 1, 1)));
        assertTrue("Size is 1 after one insert", list.getSize() == 1);

        list.insert(node(LocalDate.of(2024, 6, 1)));
        list.insert(node(LocalDate.of(2024, 3, 1)));
        assertTrue("Size is 3 after three inserts", list.getSize() == 3);
    }

    static void testSearch() {
        System.out.println("\n--- Search ---");

        SinglyLinkedList list = new SinglyLinkedList();
        LocalDate target = LocalDate.of(2024, 8, 10);
        list.insert(node(LocalDate.of(2024, 1, 1)));
        list.insert(node(target));
        list.insert(node(LocalDate.of(2024, 12, 31)));

        WorkoutNode found = list.search(target);
        assertTrue("search() finds existing date", found != null);
        assertTrue("search() returns correct node", found.getDate().equals(target));

        WorkoutNode notFound = list.search(LocalDate.of(2024, 5, 5));
        assertTrue("search() returns null for missing date", notFound == null);
    }

    static void testRangeQuery() {
        System.out.println("\n--- Range Query ---");

        SinglyLinkedList list = new SinglyLinkedList();
        // Insert Jan through Dec
        for (int month = 1; month <= 12; month++) {
            list.insert(node(LocalDate.of(2024, month, 1)));
        }

        // Query April through August (inclusive start, exclusive end per implementation)
        List<WorkoutNode> results = list.rangeQuery(
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 8, 1)
        );

        assertTrue("Range query returns correct count (Apr–Aug inclusive)",
                results.size() == 5);

        // Verify all results are within range
        boolean allInRange = results.stream().allMatch(w ->
            !w.getDate().isBefore(LocalDate.of(2024, 4, 1)) &&
            !w.getDate().isAfter(LocalDate.of(2024, 8, 1))
        );
        assertTrue("All range query results are within bounds", allInRange);
    }

    static void testEdgeCases() {
        System.out.println("\n--- Edge Cases ---");

        // Single element
        SinglyLinkedList single = new SinglyLinkedList();
        single.insert(node(LocalDate.of(2024, 6, 15)));
        assertTrue("Single-element list has size 1", single.getSize() == 1);
        assertTrue("Search works on single-element list",
                single.search(LocalDate.of(2024, 6, 15)) != null);

        // Clear
        single.clear();
        assertTrue("Size is 0 after clear()", single.getSize() == 0);
        assertTrue("Search returns null after clear()",
                single.search(LocalDate.of(2024, 6, 15)) == null);

        // Insert at head (earlier than all existing)
        SinglyLinkedList list = new SinglyLinkedList();
        list.insert(node(LocalDate.of(2024, 6, 1)));
        list.insert(node(LocalDate.of(2024, 3, 1)));  // should become new head
        List<WorkoutNode> all = list.rangeQuery(
            LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1));
        assertTrue("Earlier date becomes head of list",
                all.get(0).getDate().equals(LocalDate.of(2024, 3, 1)));
    }
}
