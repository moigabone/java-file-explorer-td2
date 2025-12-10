package com.esiea.pootd2.commands;

/**
 * Represents the 'cd' command to change the current directory.
 [cite_start]* [cite: 91]
 */
public class ChangeDirectoryCommand extends Command {
    
    /**
     * The destination path (e.g., "../home" or "/").
     */
    public String path;

    public ChangeDirectoryCommand(String path) {
        this.path = path;
    }
}