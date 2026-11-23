#!/bin/bash
echo "==================================================="
echo "  Compiling and Launching KuralVerse Java Application"
echo "==================================================="

javac ThirukkuralServer.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Java compilation failed!"
    exit 1
fi

echo "Compilation successful! Launching server..."
java ThirukkuralServer
