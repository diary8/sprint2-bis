#!/bin/bash

# Répertoire des sources
SRC_DIR="src/main/java"

# Répertoire de sortie pour les classes compilées
OUT_DIR="out/target"

# Crée le dossier de sortie s'il n'existe pas
mkdir -p "$OUT_DIR"

# Compile tous les fichiers .java
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

# Message
echo "Compilation terminée. Classes générées dans $OUT_DIR"

java -cp out/target test.MainTest
