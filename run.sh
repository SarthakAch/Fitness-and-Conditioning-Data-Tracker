#!/bin/bash
# Run script for Fitness Tracker

# Compile first
./compile.sh

if [ $? -eq 0 ]; then
    echo ""
    echo "Starting Fitness Tracker..."
    echo ""
    java -cp bin Main
fi
