@echo off
REM Compile script for Windows

echo Compiling Fitness ^& Conditioning Data Tracker...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin src\models\*.java src\structures\*.java src\utils\*.java src\ui\*.java src\Main.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Run with: java -cp bin Main
) else (
    echo Compilation failed!
    exit /b 1
)
