# Getting Started Guide

## Quick Start

### Compile and Run
```bash
# On macOS/Linux:
./compile.sh
./run.sh

# On Windows:
compile.bat
run.bat

# Or manually:
javac -d bin src/**/*.java src/*.java
java -cp bin Main
```

## Project Structure Overview

```
Fitness-Tracker/
├── src/
│   ├── Main.java                      # Entry point
│   ├── models/
│   │   └── WorkoutNode.java          # Data model (Rami)
│   ├── structures/
│   │   ├── AVLTree.java              # Self-balancing tree (Cesar + Sarthak)
│   │   ├── UnbalancedBST.java        # Baseline BST (Rami)
│   │   └── SinglyLinkedList.java     # Baseline list (Rami)
│   ├── utils/
│   │   ├── MockDataGenerator.java    # Test data (Jarmaine)
│   │   └── PerformanceBenchmark.java # Benchmarking (Jarmaine)
│   └── ui/
│       └── CommandLineInterface.java # User interface (Jarmaine)
├── README.md                          # Project documentation
├── TEAM_TASKS.md                      # Individual assignments
└── GETTING_STARTED.md                 # This file
```

## What's Already Done ✓

All base code is complete! Each file has:
- Full implementation of core functionality
- Comments indicating who owns each section
- TODO items for testing and optimization

## Individual Quick Start Guides

### Rami - Start Here

Your files are **100% complete**:
- ✓ `WorkoutNode.java` - Data model
- ✓ `UnbalancedBST.java` - Binary search tree
- ✓ `SinglyLinkedList.java` - Linked list

**Your Tasks:**
1. Review the code and understand the logic
2. Create test cases to verify functionality
3. Document the O(n) degradation in BST with sequential inserts

**Test Your Code:**
```java
// Create a simple test
WorkoutNode w1 = new WorkoutNode(LocalDate.now(), 1500.0, 30, 75.0);
System.out.println(w1);

UnbalancedBST bst = new UnbalancedBST();
bst.insert(w1);
WorkoutNode found = bst.search(LocalDate.now());
```

---

### Cesar - Start Here

Your sections in `AVLTree.java` are **100% complete**:
- ✓ Height calculations
- ✓ Balance factor methods
- ✓ All four rotations (Left, Right, Left-Right, Right-Left)
- ✓ AVL insertion with balancing

**Your Tasks:**
1. Review the rotation algorithms
2. Test each rotation type individually
3. Verify balance factors stay between -1 and 1
4. Create visual diagrams of rotations

**Test Your Code:**
```java
AVLTree avl = new AVLTree();
// Insert sequential dates - should trigger rotations
for (int i = 0; i < 10; i++) {
    LocalDate date = LocalDate.now().plusDays(i);
    WorkoutNode w = new WorkoutNode(date, 1000.0, 30, 75.0);
    avl.insert(w);
}
System.out.println("Size: " + avl.getSize());
```

---

### Sarthak - Start Here

Your sections in `AVLTree.java` are **100% complete**:
- ✓ AVL deletion with cascading rebalancing
- ✓ Range query with custom in-order traversal
- ✓ Search functionality

**Your Tasks:**
1. Test deletion thoroughly (leaf, one child, two children)
2. Test range queries with various date ranges
3. Verify tree remains balanced after deletions
4. Optimize range query performance

**Test Your Code:**
```java
AVLTree avl = new AVLTree();
// Add 30 days of data
for (int i = 0; i < 30; i++) {
    LocalDate date = LocalDate.now().minusDays(30 - i);
    avl.insert(new WorkoutNode(date, 1000.0, 30, 75.0));
}

// Test range query
LocalDate start = LocalDate.now().minusDays(7);
LocalDate end = LocalDate.now();
List<WorkoutNode> results = avl.rangeQuery(start, end);
System.out.println("Found " + results.size() + " workouts in last 7 days");
```

---

### Jarmaine - Start Here

Your files are **100% complete**:
- ✓ `MockDataGenerator.java` - Data generation
- ✓ `PerformanceBenchmark.java` - Benchmarking
- ✓ `CommandLineInterface.java` - User interface

**Your Tasks:**
1. Test mock data generation
2. Run benchmarks with different data sizes
3. Test all UI menu options
4. Document performance results

**Test Your Code:**
```java
// Test data generation
MockDataGenerator gen = new MockDataGenerator();
List<WorkoutNode> data = gen.generateChronologicalData(100, LocalDate.now().minusDays(100));
gen.printDataSummary(data);

// Test benchmark
BenchmarkResult result = PerformanceBenchmark.benchmarkInsertion(data, "AVL");
System.out.println("Insertion time: " + result.durationNanos + " ns");
```

---

## Testing Strategy

### Unit Testing (Weeks 1-4)
Each person tests their own components:
```bash
# Create simple test in Main.java
public static void main(String[] args) {
    // Test your component here
}
```

### Integration Testing (Weeks 5-6)
Test all structures together:
1. Load same data into all three structures
2. Verify they return identical results
3. Compare performance

### Performance Testing (Weeks 7-8)
Run comprehensive benchmarks:
1. Test with 100, 500, 1000, 5000 entries
2. Measure insertion, search, range query times
3. Document results for report

## Common Issues & Solutions

### Compilation Error
```bash
# Make sure you're in the project root directory
cd "Group Project"
./compile.sh
```

### ClassNotFoundException
```bash
# Make sure to use -cp bin flag
java -cp bin Main
```

### Date Format Error
```
# Use format: yyyy-MM-dd
# Example: 2024-03-15
```

## Next Steps

1. **Week 1-2**: Each person reviews and tests their assigned files
2. **Week 3**: Team meeting to integrate and test together
3. **Week 4**: Run initial benchmarks
4. **Week 5-6**: Optimize and add more tests
5. **Week 7-8**: Final testing, documentation, presentation

## Questions?

- Check `TEAM_TASKS.md` for detailed task breakdown
- Check `README.md` for project overview
- Review code comments for implementation details
- Test with small examples first before large datasets

## Git Workflow (Recommended)

```bash
# Each person works on their branch
git checkout -b rami-baseline-structures
git checkout -b cesar-avl-rotations
git checkout -b sarthak-avl-queries
git checkout -b jarmaine-ui-benchmark

# Commit your changes
git add .
git commit -m "Implemented AVL rotations"

# Push to GitHub
git push origin your-branch-name

# Create pull request for team review
```

Good luck! 🚀
