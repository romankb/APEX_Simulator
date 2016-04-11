/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

import apexsimulator.util.ArchRegisterEnum;

import java.util.Map;
import java.util.TreeMap;

/**
 * Architectural register file
 * Sole responsibility is to provide read and update access
 * to register cells through Architectural Enums defined in util.
 * Will be package private so there is no need for encapsulating members
 *
 * @author Roman Kurbanov
 */
class ARF {
    private Map<ArchRegisterEnum,Register> arf;

    /**
     * Constructs empty ARF
     */
    public ARF() {
        arf = new TreeMap<>();
    }

    /**
     * resets registers
     */
    public void reload() {
        arf.clear();
    }

    /**
     * Prints used registers
     */
    public void display() {
        System.out.print("ARF: ( ");

        // printing only "dirty" items
        for (Map.Entry<ArchRegisterEnum, Register> entry: arf.entrySet()) {
            System.out.printf("[%s: %d] ", entry.getKey(), entry.getValue().getValue());
        }
        System.out.println(")");
    }

    /**
     * commitment simply updates ARF by adding new member inside it
     * @param archReg name of register to update
     * @param regIn register
     */
    public void commit(ArchRegisterEnum archReg, Register regIn) {
        arf.put(archReg, regIn);
    }

    /**
     * Getter for a ARF
     * @param archReg name of register to read
     * @return null or needed register
     */
    public Register readRegister(ArchRegisterEnum archReg) {
        return arf.get(archReg);
    }
}
