package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.controllers.IExplorerController;
import com.esiea.pootd2.interfaces.*;

/**
 * Main entry point of the application.
 * It initializes the core logic and selects the user interface to use.
 */
public class ExplorerApp {
    
    public static void main(String[] args) {
        // 1. Initialize the controller (the brain of the application)
        IExplorerController controller = new ExplorerController();
        IUserInterface ui;

        // 2. Select the interface based on command-line arguments
        // If the user runs with "http" argument, start the web server.
        // Otherwise, default to the standard text console.
        if (args.length > 0 && "http".equals(args[0])) {
            ui = new HttpInterface(controller);
        } else {
            ui = new TextInterface(controller);
        }

        // 3. Start the application loop
        ui.run();
    }
}