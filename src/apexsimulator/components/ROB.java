/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */


package apexsimulator.components;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.InstructionStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * Reorder buffer
 *
 * @author Roman Kurbanov
 */
public class ROB {
    private LinkedList<Instruction> robBuffer;

    public ROB() {
        robBuffer = new LinkedList<>();
    }

    public boolean full() {
        return robBuffer.size() == GlobalVars.ROB_SIZE;
    }

    public boolean empty() {
        return robBuffer.size() == 0;
    }

    public void addInstruction (Instruction instrIn) {
        robBuffer.addLast(instrIn);
    }

    public void commit() {
        if (empty())    return;

        if (robBuffer.getFirst().getStatus() == InstructionStatus.Completed) {
            // do smth
        }

    }

    public void reload() {
        robBuffer.clear();
    }
}
