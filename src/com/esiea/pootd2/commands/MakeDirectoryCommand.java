package com.esiea.pootd2.commands;

/**
 * Represents the 'mkdir' command to create a new folder.
 */
public class MakeDirectoryCommand extends Command {
    
    /**
     * The name of the folder to create.
     */
    public String folderName;

    public MakeDirectoryCommand(String folderName) {
        this.folderName = folderName;
    }
}