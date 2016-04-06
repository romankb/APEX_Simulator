/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * Fetch1 stage
 * Sets PC for instruction and advances global fetch PC value
 *
 * @author Roman Kurbanov
 */
public class Fetch1 implements StageInterface{
    private Instruction instruction;
    private RegisterFile rf;
    private int produce;
    private int consume;

    /**
     * Gets instance of register file to figure out program counter
     */
    public Fetch1() {
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

        // Next stage hasn't consumed it
        if (rf.production[produce]!=null) {
            return;
        }



        // Normal work
        instruction = new Instruction();
        int pcVal = rf.getFetchPC();
        instruction.setPC(pcVal);
        rf.setFetchPC(++pcVal);
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
            System.out.print("[F1]:EMPTY; ");
        } else {
            System.out.printf("[F1]:PC %d; ", instruction.getPC());
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
