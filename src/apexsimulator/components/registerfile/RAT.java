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
    private Map<ArchRegisterEnum, Register> rat_backup;
    private boolean backed = false;


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

        rat_backup = new TreeMap<>();
        backed = false;
    }

    /**
     * resets internal structure
     */
    public void reload() {
        arf.reload();
        prf.reload();
        rat.clear();
        rat_backup.clear();
        Register temp = new Register();
        temp.setUsed(true);
        temp.setValid(true);
        temp.setValue(1);
        commit(ArchRegisterEnum.Z, temp);
        backed = false;
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
        arf.display();
    }

    public void back() {
        if (!backed) {
            rat_backup.putAll(rat);
            backed = true;
        }
    }

    public void success() {
        rat_backup.clear();
        backed = false;
    }

    public void failure() {
        rat.clear();
        rat.putAll(rat_backup);
        backed = false;
    }

}
