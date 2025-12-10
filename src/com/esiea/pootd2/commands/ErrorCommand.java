package com.esiea.pootd2.commands;

/**
 * Special command used when an error occurs (like a typo or unknown command).
 * Instead of crashing, we pass this object containing the error details.
 */
public class ErrorCommand extends Command {
    
    /**
     * The error message to display to the user.
     */
    public String message;

    public ErrorCommand(String message) {
        this.message = message;
    }
}