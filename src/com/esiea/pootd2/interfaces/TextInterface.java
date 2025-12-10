package com.esiea.pootd2.interfaces;

import java.util.Scanner;
import com.esiea.pootd2.controllers.IExplorerController;

public class TextInterface extends AbstractInterface {

    public TextInterface(IExplorerController controller) {
        super(controller);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                if ("exit".equals(input)) {
                    running = false;
                } else {
                    String result = controller.executeCommand(input);
                    System.out.print(result);
                }
            } else {
                running = false;
            }
        }
        scanner.close();
    }
}