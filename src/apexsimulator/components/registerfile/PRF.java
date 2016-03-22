/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

/**
 * Physical register file
 * Sole responsibility is to provide read and update access
 * to register cells.
 * Will be package private so there is no need for encapsulating members
 *
 * @author Roman Kurbanov
 */
class PRF {
    public Register[] prf;

    /**
     * Constructs empty PRF
      */
    public PRF() {
        prf = new Register[GlobalVars.PHYS_REG_COUNT];
        for (int i = 0; i < GlobalVars.PHYS_REG_COUNT; ++i) {
            prf[i] = new Register();
        }
    }

    /**
     * resets registers
     */
    public void reload() {
        for (int i = 0; i < GlobalVars.PHYS_REG_COUNT; ++i) {
            prf[i].reload();
        }
    }

    /**
     * Prints used registers
     */
    public void display() {
        System.out.print("PRF: ( ");

        for (int i = 0; i < GlobalVars.PHYS_REG_COUNT; ++i) {
            if (prf[i].isUsed()) {
                if (prf[i].isValid()) {
                    System.out.printf("[P%d: %d] ", i, prf[i].getValue());
                } else {
                    System.out.printf("[P%d: not ready] ", i);
                }
            }
        }
        System.out.println(")");
    }
}
