package com.esiea.pootd2.commands;

public class TouchCommand extends Command {
    public String fileName;

    public TouchCommand(String fileName) {
        this.fileName = fileName;
    }
}