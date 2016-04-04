/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * Decode/Dispatch stage 2
 * Dispatches instruction
 *
 * @author Roman Kurbanov
 */
public class Decode2 implements StageInterface{
    Instruction instruction;
    RegisterFile rf;

    private int produce;
    private int consume;

    public Decode2() {
        rf = RegisterFile.getInstance();
    }

    /**
     * Clock cycle received
     */
    @Override
    public void nextCycle() {
        // end of operations
        if (GlobalVars.pipeline_frozen) {
            return;
        }

        // Next stage hasn't consumed it or data not available
        if (rf.production[produce]!=null || rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];
        // do smth with instruction lat
        rf.production[consume] = null;
        rf.production[produce] = instruction;
    }

    /**
     * Clears stage
     */
    @Override
    public void clear() {
        instruction = null;
        rf.production[produce] = null;
    }

    /**
     * Displays stage
     */
    @Override
    public void display() {
        if (instruction==null) {
            System.out.print("[D2]:EMPTY; ");
        } else {
            System.out.printf("[D2]:%s; ", instruction.getInstruction());
        }
    }

    /**
     * Id of stage
     * @param id unique number of the stage
     */
    @Override
    public void setId(int id) {
        produce = id;
        consume = id -1;
    }
}
