package com.esiea.pootd2.controllers;

import com.esiea.pootd2.models.*;
import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.commands.parsers.*;

/**
 * The Controller manages the file system state and executes logic.
 * It holds the current folder and applies changes based on user commands.
 */
public class ExplorerController implements IExplorerController {
    private FolderInode root;
    private FolderInode currentFolder; // Keeps track of where the user is currently located
    private ICommandParser parser;

    public ExplorerController() {
        // Initialize the file system root and start at the root ("/")
        this.root = new FolderInode("/");
        this.currentFolder = this.root;
        this.parser = new UnixLikeCommandParser();
    }

    /**
     * Main entry point: converts text to a command and executes it.
     */
    @Override
    public String executeCommand(String commandStr) {
        // 1. Parse the text into a Command object
        Command cmd = parser.parse(commandStr);
        
        // 2. Execute the specific logic for that command
        return doCommand(cmd);
    }

    /**
     * Dispatches the command to the right logic based on its type.
     * We use 'instanceof' to check which specific command we received.
     */
    private String doCommand(Command cmd) {
        if (cmd instanceof ListCommand) {
            // Build a string listing all children of the current folder
            StringBuilder sb = new StringBuilder();
            for (Inode inode : currentFolder.getChildren()) {
                sb.append(inode.getName()).append("\n");
            }
            return sb.toString();
        }
        else if (cmd instanceof MakeDirectoryCommand) {
            // Cast the command to access the specific 'folderName' field
            String name = ((MakeDirectoryCommand) cmd).folderName;
            FolderInode newFolder = new FolderInode(name);
            currentFolder.addInode(newFolder);
            return ""; // No output needed for success
        }
        else if (cmd instanceof TouchCommand) {
            String name = ((TouchCommand) cmd).fileName;
            FileInode newFile = new FileInode(name);
            currentFolder.addInode(newFile);
            return "";
        }
        else if (cmd instanceof ChangeDirectoryCommand) {
            return handleChangeDirectory(((ChangeDirectoryCommand) cmd).path);
        }
        else if (cmd instanceof ErrorCommand) {
            return ((ErrorCommand) cmd).message + "\n";
        }
        return "Command not implemented yet\n";
    }

    /**
     * Handles navigation logic (root, parent, or child folder).
     */
    private String handleChangeDirectory(String path) {
        // 1. Return to root
        if (path.equals("/")) {
            currentFolder = root;
            return "";
        }
        // 2. Go to parent folder (if it exists)
        if (path.equals("..")) {
            if (currentFolder.getParent() != null) {
                currentFolder = currentFolder.getParent();
            }
            return "";
        }

        // 3. Search for a child folder with the given name
        for (Inode inode : currentFolder.getChildren()) {
            // We must check if it is a FolderInode before moving into it
            if (inode instanceof FolderInode && inode.getName().equals(path)) {
                currentFolder = (FolderInode) inode;
                return "";
            }
        }
        return "cd: no such file or directory: " + path + "\n";
    }
}