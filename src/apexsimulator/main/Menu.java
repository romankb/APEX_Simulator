/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.main;

import apexsimulator.util.ErrorCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class implements a menu to interact with user
 *
 * @author Roman Kurbanov
 */
public class Menu implements MenuInterface{

    private int input;
    private BufferedReader inp;

    /**
     * Sets buffered reader to console input
     */
    public Menu() {
        input = 0;
        inp = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Get user input
     *
     * @return user selection
     */
    @Override
    public MenuCodes readCode() {
        String tempInput;

        // try to read until successful
        while (true) {
            printInvitation();

            tempInput = getString();
            try {
                input = Integer.parseInt(tempInput);
                if (input<1 || input>4) {
                    System.out.println();
                    System.out.println("Error! Please enter integer from the range [1-4]");
                    //displayMenu();
                } else {
                    break;
                }
            } catch (NumberFormatException nf) {
                System.out.println();
                System.out.println("Error! Please enter integer from the range [1-4]");
                //displayMenu();
            }
        }

        switch (input){
            case 1: return MenuCodes.Initialize;
            case 2: return MenuCodes.Simulate;
            case 3: return MenuCodes.Display;
        }
        return  MenuCodes.Exit;
    }

    /**
     * Display menu items to screen
     */
    @Override
    public void displayMenu() {
        System.out.println();
        System.out.println("-----=======APEX Simulator Menu=======------");
        System.out.println("[1] Initialize");
        System.out.println("[2] Simulate");
        System.out.println("[3] Display");
        System.out.println("[4] Quit");
        System.out.println();
    }

    /**
     * If simulation was chosen read number of cycles
     * does all error checking
     *
     * @return number of cycles to simulate
     */
    @Override
    public int readCycles() {
        int cycles;
        String tempInput;

        // try to read until successful
        while (true) {
            System.out.println("Please enter number of cycles to simulate:");

            tempInput = getString();
            try {
                cycles = Integer.parseInt(tempInput);
                if (cycles<0) {
                    System.out.println();
                    System.out.println("Error! Please enter positive integer");
                } else {
                    break;
                }
            } catch (NumberFormatException nf) {
                System.out.println();
                System.out.println("Error! Please enter valid integer");
                //displayMenu();
            }
        }

        return cycles;
    }

    // refactored out repeated code. testing needed
    private String getString() {
        String tempInput=null;
        try {
            tempInput = inp.readLine();
        } catch (IOException e) {
            System.out.println("Internal software error.");
            System.out.println("Exiting...");
            System.exit(ErrorCodes.CONSOLE_ERROR);
        }
        return tempInput;
    }

    /**
     * helper method to write descriptive message after menu
     */
    private void printInvitation() {
        System.out.println("Please enter menu choice:");
    }
}
