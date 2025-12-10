# TD2 - File Explorer (Java)

Author: Gabin Clavier (ESIEA / Ecritel)
Course: Object-Oriented Programming (INF3132)

This project is an implementation of a virtual Unix-like file system. It follows the MVC (Model-View-Controller) architecture and utilizes several design patterns (Composite, Strategy, Command).

## Features

The application allows users to navigate and modify a virtual file tree via text commands.

- ls: List the content of the current directory.
- cd <path>: Change directory (supports `..` and `/`).
- mkdir <name>: Create a new directory.
- touch <name>: Create a new file.
- exit: Quit the application.

##Prerequisites

- java 21 (or higher).
- No external libraries required (Standard Java only).

## Installation & Compilation

Navigate to the project root (where the `src` folder is located).

### On Linux, macOS, or WSL (Recommended)
Use this command to compile the entire project:
javac -d classes $(find src -name "*.java")

### Console Mode (Standard)
To launch the classic text interface (terminal style):
java -cp classes com.esiea.pootd2.ExplorerApp

### Web Mode (HTTP Bonus)
To launch the interface via a web browser:
java -cp classes com.esiea.pootd2.ExplorerApp http
Once launched, open your browser at: http://localhost:8001

![UML](TD2UML.png)
