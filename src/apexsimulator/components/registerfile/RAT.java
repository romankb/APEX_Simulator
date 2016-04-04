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
 * Rename table that will keep track of free list,
 * allocating free register, committing register to ARF,
 * restoring PRF on branch misprediction, and maintaining
 * a register alias table.
 *
 * @author Roman Kurbanov
 */
public class RAT {

    /**
     * Data members
     */
    private ARF arf;
    private PRF prf;
    private Map<ArchRegisterEnum, Register> rat;


    /**
     * Constructs empty RAT table.
     */
    public RAT() {
        arf = new ARF();
        prf = new PRF();
        rat = new TreeMap<>();

    }

    /**
     * resets internal structure
     */
    public void reload() {
        arf.reload();
        prf.reload();
        rat.clear();
    }

    /**
     * returns valid mapping
     * @param archRegIn name of register
     * @return returns valid mapping or null if mapping doesn't exist
     */
    public Register getLatest(ArchRegisterEnum archRegIn) {
        return rat.get(archRegIn);
    }

    public Register assignFree(ArchRegisterEnum archRegIn) {
        Register temp = prf.getFree();
        if (temp!=null) {
            rat.put(archRegIn, temp);
        }
        return temp;
    }

    public void commit(ArchRegisterEnum archRegIn, Register regIn) {
        arf.commit(archRegIn, regIn.getValue());
        Register temp = arf.readRegister(archRegIn);
        if (temp!=null) {
            rat.put(archRegIn, temp);
        } else {
            System.out.println("Commiting failed. Exiting...");
            System.exit(123);
        }
    }

    public void display() {
        prf.display();
        arf.display();
    }


}
