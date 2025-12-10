package com.esiea.pootd2.controllers;

/**
 * Interface for the main controller.
 * It connects the User Interface (Text or HTTP) to the internal logic.
 */
public interface IExplorerController {

    /**
     * Executes a command line (like "ls" or "mkdir test") and returns the result to display.
     */
    String executeCommand(String commandStr);
}