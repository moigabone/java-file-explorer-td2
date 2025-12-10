package com.esiea.pootd2.commands;

public class MakeDirectoryCommand extends Command {
    public String folderName;

    public MakeDirectoryCommand(String folderName) {
        this.folderName = folderName;
    }
}