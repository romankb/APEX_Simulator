/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.main;

import apexsimulator.components.memory.Memory;
import apexsimulator.util.ErrorCodes;

/**
 * Entry point of the program. Does simple usage checking
 * and starts the execution
 *
 * @author Roman Kurbanov
 */
public class Driver {

    /**
     * Instantiates controller and controls commands sent to it
     * @param args file name to load
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <apexsimulator> inputFileName");
            System.exit(ErrorCodes.USAGE_ERROR);
        }

        Memory.getInstance().setFileName(args[0]);
        Memory.getInstance().loadProgram();
        Controller controller = new Controller();
        controller.startSimulator();
    }
}
