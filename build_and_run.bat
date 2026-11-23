@echo off
echo ===================================================
echo   Compiling and Launching KuralVerse Java Application
echo ===================================================

javac ThirukkuralServer.java
if %errorlevel% neq 0 (
    echo [ERROR] Java compilation failed!
    exit /b %errorlevel%
)

echo Compilation successful! Launching server...
java ThirukkuralServer
