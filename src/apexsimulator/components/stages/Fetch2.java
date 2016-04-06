/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.memory.Memory;
import apexsimulator.components.memory.MemoryInterface;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * Fetch2 stage fetches instruction from memory
 *
 * @author Roman Kurbanov
 */
public class Fetch2 implements StageInterface{

    private MemoryInterface memory;
    private RegisterFile rf;
    private Instruction instruction;
    private int produce;
    private int consume;

    /**
     * Gets instance of memory
     */
    public Fetch2() {
        memory = Memory.getInstance();
        rf = RegisterFile.getInstance();
    }


    /**
     * Fetches instruction from memory
     */
    @Override
    public void nextCycle() {
        instruction = null;
        // end of operations
        if (GlobalVars.pipeline_frozen) {
            return;
        }

        // Next stage hasn't consumed it or data not available
        if (rf.production[produce]!=null || rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];
        instruction.setInstruction(memory.fetch(instruction.getPC()));
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
     * Prints status to console
     */
    @Override
    public void display() {
        if (instruction==null) {
            System.out.print("[F2]:EMPTY; ");
        } else {
            System.out.printf("[F2]:%s; ", instruction.getInstruction());
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
