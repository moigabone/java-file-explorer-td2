package com.esiea.pootd2.interfaces;

import com.esiea.pootd2.controllers.IExplorerController;

/**
 * Base class for all user interfaces (Text or Web).
 * It holds the common logic, specifically the reference to the controller.
 */
public abstract class AbstractInterface implements IUserInterface {
    
    /**
     * The controller that handles the logic.
     * 'protected' allows child classes (like TextInterface) to access it directly.
     */
    protected IExplorerController controller;

    public AbstractInterface(IExplorerController controller) {
        this.controller = controller;
    }

}