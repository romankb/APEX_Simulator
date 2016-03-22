/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.Instruction;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * Fetch1 stage
 * Sets PC for instruction and advances global fetch PC value
 *
 * @author Roman Kurbanov
 */
public class Fetch1 implements StageInterface, DisplayInterface{
    private Instruction instruction;
    private RegisterFile rf;

    /**
     * Gets instance of register file to figure out program counter
     */
    public Fetch1() {
        rf = RegisterFile.getInstance();
    }


    /**
     * Fetches instruction from memory
     * @param instruction instruction to be operated on
     */
    @Override
    public void advance(Instruction instruction) {
        this.instruction = instruction;
        if (instruction == null)    return;
        int pcVal = rf.getFetchPC();
        instruction.setPC(pcVal);
        rf.setFetchPC(++pcVal);
    }

    /**
     * Prints status to console
     */
    @Override
    public void display() {
        if (instruction==null) {
            System.out.print("[F1]:EMPTY; ");
        } else {
            System.out.printf("[F1]:PC%d; ", instruction.getPC());
        }
    }
}
