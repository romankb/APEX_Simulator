/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.main;

import apexsimulator.components.Datapath;
import apexsimulator.components.DatapathInterface;

/**
 * This class implements main controller logic of the simulator
 *
 * @author Roman Kurbanov
 */
public class Controller {

    private DatapathInterface datapath;
    private MenuInterface menu;

    /**
     * Instantiates datapath and creates menu
     */
    public Controller() {
        datapath = new Datapath();
        menu = new Menu();
    }


    public void startSimulator() {
        MenuCodes res = null;
        int cycles = 0;
        while(true) {
            menu.displayMenu();
            res = menu.readCode();

            if(res == MenuCodes.Initialize) {
                datapath.initialize();
                System.out.println("Initialized successfully. Datapath reloaded");
            } else if (res == MenuCodes.Simulate) {
                cycles = menu.readCycles();
                System.out.println("Simulating "+cycles+" number of cycles");
                datapath.simulate(cycles);
            } else if (res == MenuCodes.Display) {
                System.out.println();
                System.out.println("Displaying contents of datapath components");
                datapath.display();
            } else {
                System.out.println("Program terminated by user.");
                System.out.println("Good bye.");
                System.exit(0);

            }

        }
    }

}
