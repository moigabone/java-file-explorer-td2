package com.esiea.pootd2.commands;

/**
 * Represents the 'touch' command to create a new file.
 */
public class TouchCommand extends Command {
    
    /**
     * The name of the file to create.
     */
    public String fileName;

    public TouchCommand(String fileName) {
        this.fileName = fileName;
    }
}