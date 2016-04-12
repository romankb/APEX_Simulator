/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

import apexsimulator.util.ArchRegisterEnum;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
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
    private Map<ArchRegisterEnum, LinkedList <Register>> rat;


    /**
     * Constructs empty RAT table.
     */
    public RAT() {
        arf = new ARF();
        prf = new PRF();
        rat = new TreeMap<>();

        // creating Z flag which will be 1
        Register temp = new Register();
        temp.setUsed(true);
        temp.setValid(true);
        temp.setValue(1);
        commit(ArchRegisterEnum.Z, temp);

    }

    /**
     * resets internal structure
     */
    public void reload() {
        arf.reload();
        prf.reload();
        rat.clear();
        Register temp = new Register();
        temp.setUsed(true);
        temp.setValid(true);
        temp.setValue(1);
        commit(ArchRegisterEnum.Z, temp);
    }

    /**
     * returns valid mapping
     * @param archRegIn name of register
     * @return returns valid mapping or null if mapping doesn't exist
     */
    public Register getLatest(ArchRegisterEnum archRegIn) {
        try {
            return rat.get(archRegIn).getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Register assignFree(ArchRegisterEnum archRegIn) {
        Register temp = prf.getFree();
        if (temp!=null) {
            if (rat.get(archRegIn) == null) {
                rat.put(archRegIn, new LinkedList<>());
            }
            rat.get(archRegIn).addFirst(temp);
            temp.setUsed(true);
        }
        return temp;
    }

    public void commit(ArchRegisterEnum archRegIn, Register regIn) {
        arf.commit(archRegIn, regIn);
        Register temp = arf.readRegister(archRegIn);
        if (temp!=null) {
            //rat.put(archRegIn, temp);
            prf.prf.remove(regIn);
            temp.physId = -1;
            prf.prf.addLast(new Register());
        } else {
            System.out.println("Commiting failed. Exiting...");
            System.exit(123);
        }
    }

    public void display() {
        prf.display();
        System.out.println();
        arf.display();
    }

    /**
     * Removes specific mapping from the RAT, useful for branches
     * @param archRegIn name of register
     * @param reg Register to be deleted from rename
     */
    public void deleteRename(ArchRegisterEnum archRegIn, Register reg) {
        rat.get(archRegIn).remove(reg);
    }
}
