/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.Instruction;
import apexsimulator.components.memory.Memory;
import apexsimulator.components.memory.MemoryInterface;

/**
 * Fetch2 stage fetches instruction from memory
 *
 * @author Roman Kurbanov
 */
public class Fetch2 implements StageInterface, DisplayInterface{

    private MemoryInterface memory;
    private Instruction instruction;

    /**
     * Gets instance of memory
     */
    public Fetch2() {
        memory = Memory.getInstance();
    }


    /**
     * Fetches instruction from memory
     * @param instruction instruction to be operated on
     */
    @Override
    public void advance(Instruction instruction) {
        this.instruction = instruction;
        if (instruction==null) return;
        instruction.setInstruction(memory.fetch(instruction.getPC()));
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
}
