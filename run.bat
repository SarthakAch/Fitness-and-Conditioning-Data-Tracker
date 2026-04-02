@echo off
REM Run script for Windows

call compile.bat

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Starting Fitness Tracker...
    echo.
    java -cp bin Main
)
