package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.controllers.IExplorerController;
import com.esiea.pootd2.interfaces.*;

public class ExplorerApp {
    public static void main(String[] args) {
        IExplorerController controller = new ExplorerController();
        IUserInterface ui;

        if (args.length > 0 && "http".equals(args[0])) {
            ui = new HttpInterface(controller);
        } else {
            ui = new TextInterface(controller);
        }

        ui.run();
    }
}