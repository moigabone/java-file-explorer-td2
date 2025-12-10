package com.esiea.pootd2.interfaces;

import java.util.Scanner;
import com.esiea.pootd2.controllers.IExplorerController;

/**
 * Implementation of the user interface for the standard terminal (console).
 * It reads user input from System.in and prints results to System.out.
 */
public class TextInterface extends AbstractInterface {

    public TextInterface(IExplorerController controller) {
        super(controller); // Pass the controller to the parent AbstractInterface
    }

    /**
     * Main loop of the application.
     * Displays a prompt, waits for input, executes commands, and prints the result.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> "); // Display the prompt character
            
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine(); // Read the entire line typed by the user

                if ("exit".equals(input)) {
                    running = false; // Check for exit condition to break the loop
                } else {
                    // Delegate the command execution to the controller
                    String result = controller.executeCommand(input);
                    System.out.print(result);
                }
            } else {
                running = false; // Stop if the input stream is closed
            }
        }
        scanner.close();
    }
}