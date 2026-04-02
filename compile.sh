#!/bin/bash
# Compile script for Fitness Tracker

echo "Compiling Fitness & Conditioning Data Tracker..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin src/**/*.java src/*.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo "Run with: java -cp bin Main"
else
    echo "✗ Compilation failed!"
    exit 1
fi
