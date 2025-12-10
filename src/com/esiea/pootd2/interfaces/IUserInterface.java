package com.esiea.pootd2.interfaces;

/**
 * Common interface for all user interfaces (Text or HTTP).
 * It ensures the main app can start any interface without knowing its specific type.
 */
public interface IUserInterface {

    /**
     * Starts the interface main loop (waiting for user input).
     */
    void run();
}