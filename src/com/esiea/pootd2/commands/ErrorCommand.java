package com.esiea.pootd2.commands;

public class ErrorCommand extends Command {
    public String message;

    public ErrorCommand(String message) {
        this.message = message;
    }
}