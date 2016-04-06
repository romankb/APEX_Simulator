/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

import java.util.LinkedList;
import java.util.List;

/**
 * Physical register file
 * Sole responsibility is to provide read and update access
 * to register cells.
 * Will be package private so there is no need for encapsulating members
 *
 * @author Roman Kurbanov
 */
class PRF {
    public LinkedList<Register> prf;

    /**
     * Constructs empty PRF
      */
    public PRF() {
        prf = new LinkedList<>();
        for (int i = 0; i < GlobalVars.PHYS_REG_COUNT; ++i) {
            prf.addLast(new Register());
        }
    }

    /**
     * resets registers
     */
    public void reload() {
        for (Register reg:prf ) {
            reg.reload();
        }
    }

    public Register getFree() {
        int i = 0;
        for (Register reg:prf ) {
            if (!(reg.isUsed())) {
                reg.physId = i;
                return reg;
            }
            i++;
        }
        return null;
    }

    /**
     * Prints used registers
     */
    public void display() {
        System.out.print("PRF: ( ");
        int i = 0;
        for (Register reg:prf ) {
            if (reg.isUsed()) {
                if (reg.isValid()) {
                    System.out.printf("[P%d: %d] ", i, reg.getValue());
                } else {
                    System.out.printf("[P%d: not ready] ", i);
                }
            } else {
                System.out.printf("[P%d: free] ", i);
            }
            i++;
        }
        System.out.println(")");
    }
}
