/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.main;

/**
 * Interface for interacting with menu
 *
 * @author Roman Kurbanov
 */
public interface MenuInterface {
    /**
     * Get user input
     * @return user selection
     */
    MenuCodes readCode();

    /**
     * Display menu items to screen
     */
    void displayMenu();

    /**
     * If simulation was chosen read number of cycles
     * does all error checking
     * @return number of cycles to simulate
     */
    int readCycles();
}
