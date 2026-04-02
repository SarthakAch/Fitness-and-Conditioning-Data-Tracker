# Team Task Distribution

## Rami Fayad - Data Model & Linked List

### Files to Work On:
- `src/models/WorkoutNode.java` ✓ (Complete)
- `src/structures/SinglyLinkedList.java` ✓ (Complete)

### Tasks:
1. **WorkoutNode Data Model** (Week 1)
   - ✓ Define fields: date, liftingVolume, cardioDuration, bodyWeight
   - ✓ Implement getters and setters
   - ✓ Add toString() method for display
   - Test with sample data

2. **Singly Linked List** (Week 1-2)
   - ✓ Implement insert() maintaining chronological order
   - ✓ Implement search() method
   - ✓ Implement rangeQuery() method
   - Document O(n) complexity

### Testing Checklist:
- [ ] Create 10 sample WorkoutNode objects
- [ ] Insert into LinkedList and verify order
- [ ] Test search on LinkedList
- [ ] Test range query on LinkedList
- [ ] Document performance characteristics

---

## Sarthak Acharya - Unbalanced BST

### Files to Work On:
- `src/structures/UnbalancedBST.java` ✓ (Complete)

### Tasks:
1. **Unbalanced BST** (Week 1-2)
   - ✓ Implement insert() method
   - ✓ Implement search() method
   - ✓ Implement rangeQuery() method
   - Test with chronological data to show O(n) degradation

### Testing Checklist:
- [ ] Insert 10 sequential dates
- [ ] Verify tree becomes skewed (right-heavy)
- [ ] Test search on BST
- [ ] Test range query on BST
- [ ] Compare performance vs LinkedList

---

## Jarmaine Higgs - AVL Tree

### Files to Work On:
- `src/structures/AVLTree.java` ✓ (Complete)

### Tasks:
1. **Height & Balance Factor** (Week 3)
   - ✓ Implement height() method
   - ✓ Implement updateHeight() method
   - ✓ Implement getBalanceFactor() method
   - Test balance factor calculations

2. **Rotation Algorithms** (Week 3)
   - ✓ Implement rotateRight()
   - ✓ Implement rotateLeft()
   - ✓ Implement rotateLeftRight()
   - ✓ Implement rotateRightLeft()
   - Create visual diagrams of each rotation

3. **AVL Insertion** (Week 4)
   - ✓ Implement insert() method
   - ✓ Implement balance() method
   - ✓ Integrate rotations with insertion
   - Ensure balance factor stays -1, 0, or 1

4. **AVL Deletion** (Week 5)
   - ✓ Implement delete() method
   - ✓ Implement deleteRecursive() with rebalancing
   - ✓ Handle three cases: no child, one child, two children
   - ✓ Implement findMin() helper
   - Test cascading rebalancing up the tree

5. **Range Query Algorithms** (Week 5-6)
   - ✓ Implement rangeQuery() method
   - ✓ Implement rangeQueryRecursive() with custom in-order traversal
   - Optimize to skip unnecessary subtrees
   - Test with various date ranges

### Testing Checklist:
- [ ] Test each rotation type individually
- [ ] Insert 100 sequential dates and verify tree stays balanced
- [ ] Check balance factor at every node after insertions
- [ ] Compare height of AVL vs BST with same data
- [ ] Verify no node has balance factor > 1 or < -1
- [ ] Test deletion of leaf nodes
- [ ] Test deletion of nodes with one child
- [ ] Test deletion of nodes with two children
- [ ] Verify tree remains balanced after deletions
- [ ] Test range query with 7-day, 30-day, 90-day windows
- [ ] Verify range query returns correct results
- [ ] Benchmark range query performance

---

## Cesar Felipe - UI, Testing & Benchmarking

### Files to Work On:
- `src/ui/CommandLineInterface.java` ✓ (Complete)
- `src/Main.java` ✓ (Complete)
- `src/utils/MockDataGenerator.java` ✓ (Complete)
- `src/utils/PerformanceBenchmark.java` ✓ (Complete)

### Tasks:
1. **Mock Data Generator** (Week 5)
   - ✓ Implement generateChronologicalData()
   - ✓ Implement generateProgressiveData()
   - ✓ Implement generateSparseData()
   - ✓ Add printDataSummary() method
   - Generate 1000+ days of realistic data

2. **Performance Benchmark** (Week 6)
   - ✓ Implement benchmarkInsertion() using System.nanoTime()
   - ✓ Implement benchmarkRangeQuery()
   - ✓ Implement benchmarkSearch()
   - ✓ Create printResults() for formatted output
   - Test with datasets of varying sizes

3. **Command-Line Interface** (Week 6-7)
   - ✓ Implement main menu system
   - ✓ Add workout entry functionality
   - ✓ Add search functionality
   - ✓ Add range query with summary statistics
   - ✓ Integrate mock data loading
   - ✓ Integrate benchmark execution
   - Add input validation and error handling

### Testing Checklist:
- [ ] Generate 100, 500, 1000 days of mock data
- [ ] Verify mock data is realistic and chronological
- [ ] Run insertion benchmark on all three structures
- [ ] Run range query benchmark (30-day window)
- [ ] Run search benchmark
- [ ] Document performance differences
- [ ] Test all UI menu options
- [ ] Test input validation (invalid dates, negative numbers)
- [ ] Verify integration with all data structures
- [ ] Test error handling and edge cases

---

## Shared Tasks (All Team Members)

### Week 7-8: Final Testing & Documentation
- [ ] Run comprehensive performance tests
- [ ] Document Big-O analysis for each operation
- [ ] Create performance comparison charts
- [ ] Write final project report
- [ ] Prepare class presentation
- [ ] Code review and cleanup
- [ ] Add comments and documentation

### Integration Testing
- [ ] Verify all three structures return identical results
- [ ] Test with 1000+ chronological entries
- [ ] Test with sparse data
- [ ] Test edge cases (empty structures, single entry)
- [ ] Verify benchmark accuracy

---

## Quick Start Guide for Each Member

### Rami - Getting Started
```bash
# 1. Test WorkoutNode
# Create test file: test/TestWorkoutNode.java
# Verify all getters/setters work

# 2. Test SinglyLinkedList
# Insert 10 dates in random order
# Verify list maintains chronological order
```

### Sarthak - Getting Started
```bash
# 1. Test UnbalancedBST
# Insert 10 sequential dates
# Verify tree becomes skewed (right-heavy)

# 2. Compare with LinkedList
# Run same operations on both structures
# Document performance differences
```

### Jarmaine - Getting Started
```bash
# 1. Test rotations visually
# Draw tree before and after each rotation
# Verify heights update correctly

# 2. Test insertion
# Insert dates: 2024-01-01, 2024-01-02, 2024-01-03
# Print tree structure after each insert
# Verify balance factor at each node

# 3. Test deletion
# Build tree with 7 nodes
# Delete leaf, then node with one child, then node with two children
# Verify tree remains balanced

# 4. Test range query
# Insert 30 days of data
# Query last 7 days
# Verify correct results returned
```

### Cesar - Getting Started
```bash
# 1. Test mock data generator
# Generate 100 days of data
# Print summary statistics
# Verify data looks realistic

# 2. Test benchmark
# Load 100 entries into each structure
# Run insertion benchmark
# Verify AVL is faster than BST and LinkedList

# 3. Test command-line interface
# Run Main.java
# Test all menu options
# Verify input validation works

# 4. Test integration
# Load mock data through UI
# Run benchmarks through UI
# Verify all features work together
```

---

## Communication & Collaboration

- **Weekly Sync**: Discuss progress and blockers
- **Code Reviews**: Review each other's code before merging
- **Testing**: Share test results and edge cases
- **Documentation**: Keep README and comments updated

## Questions?

If you're stuck or need clarification:
1. Check the proposal document
2. Review the code comments
3. Ask team members
4. Test with small examples first
