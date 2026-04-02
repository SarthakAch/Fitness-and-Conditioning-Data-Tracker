# Fitness & Conditioning Data Tracker

A high-performance fitness tracking system demonstrating the efficiency of self-balancing AVL Trees compared to unbalanced BST and Linked Lists for managing chronological workout data.

## Team Members
- **Rami Fayad** - Foundational Structures & Baselines
- **Cesar Felipe** - AVL Core & Balancing
- **Sarthak Acharya** - Advanced Tree Operations & Retrieval
- **Jarmaine Higgs** - Simulation, Benchmarking & UI

## Project Overview

This project implements three data structures to store and retrieve fitness logs:
- **AVL Tree** (main structure): Self-balancing, maintains O(log n) operations
- **Unbalanced BST** (comparison): Degrades to O(n) with chronological inserts
- **Singly Linked List** (comparison): Always O(n) for search and range queries

## Features

- Add daily workout logs (lifting volume, cardio duration, body weight)
- Search workouts by date
- Range queries to calculate summaries over any time period
- Mock data generation for testing
- Performance benchmarking with nanosecond precision
- Interactive command-line interface

## Project Structure

```
src/
├── Main.java                          # Entry point
├── models/
│   └── WorkoutNode.java              # Core data model (Rami)
├── structures/
│   ├── AVLTree.java                  # Self-balancing tree (Cesar + Sarthak)
│   ├── UnbalancedBST.java            # Baseline BST (Rami)
│   └── SinglyLinkedList.java         # Baseline list (Rami)
├── utils/
│   ├── MockDataGenerator.java        # Test data generator (Jarmaine)
│   └── PerformanceBenchmark.java     # Benchmarking tools (Jarmaine)
└── ui/
    └── CommandLineInterface.java     # User interface (Jarmaine)
```

## Team Responsibilities

### Rami (Foundational Structures & Baselines)
- [ ] Design and implement `WorkoutNode` data model
- [ ] Implement `UnbalancedBST` with insert, search, and range query
- [ ] Implement `SinglyLinkedList` with insert, search, and range query
- [ ] Test baseline structures with sample data

### Cesar (AVL Core & Balancing)
- [ ] Implement AVL node height calculations
- [ ] Implement balance factor updates
- [ ] Implement four rotation algorithms (Left, Right, Left-Right, Right-Left)
- [ ] Implement AVL insertion with automatic balancing
- [ ] Write unit tests for rotations

### Sarthak (Advanced Tree Operations & Retrieval)
- [ ] Implement AVL deletion with cascading rebalancing
- [ ] Implement range query algorithms (custom in-order traversal)
- [ ] Optimize range queries for efficiency
- [ ] Test deletion and range queries thoroughly

### Jarmaine (Simulation, Benchmarking & UI)
- [ ] Implement `MockDataGenerator` with multiple data patterns
- [ ] Implement `PerformanceBenchmark` using System.nanoTime()
- [ ] Build interactive `CommandLineInterface`
- [ ] Create comprehensive benchmark test suite
- [ ] Document performance results

## How to Run

### Compile
```bash
javac -d bin src/**/*.java src/*.java
```

### Run
```bash
java -cp bin Main
```

## Usage Example

1. **Load Mock Data**: Generate 1000 days of chronological workout data
2. **Run Benchmark**: Compare insertion and range query performance
3. **Range Query**: Calculate total lifting volume for last 30 days
4. **Search**: Find specific workout by date

## Expected Performance Results

| Operation | AVL Tree | Unbalanced BST | Linked List |
|-----------|----------|----------------|-------------|
| Insertion | O(log n) | O(n) worst case | O(n) |
| Search | O(log n) | O(n) worst case | O(n) |
| Range Query | O(log n + k) | O(n) | O(n) |

*k = number of results in range*

## Testing Strategy

1. **Unit Tests**: Verify AVL rotations maintain balance factor of -1, 0, or 1
2. **Integration Tests**: Test all three structures with identical datasets
3. **Performance Tests**: Benchmark with 1,000+ chronological entries
4. **Range Query Tests**: 30-day window queries across all structures

## Timeline

- **Weeks 1-2**: Project setup, WorkoutNode, baseline structures
- **Weeks 3-4**: AVL core logic and rotations
- **Weeks 5-6**: Range queries, mock data generator, benchmarking
- **Weeks 7-8**: Performance testing, documentation, presentation

## Deliverables

- Well-documented Java source code
- Benchmarking test suite with mock data
- Final project report with Big-O analysis
- Class presentation demonstrating findings

## License

Academic project for SENG 2000 - Data Structures & Algorithms
