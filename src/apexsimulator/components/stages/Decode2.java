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
import apexsimulator.util.InstructionStatus;

/**
 * Decode/Dispatch stage 2
 * Dispatches instruction if all renames were successfull
 * otherwise does nothing and stalls pipeline.
 *
 * Execution stage will consume and add it to appropriate queue
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
        instruction = null;
        // no need to freeze
        //if (GlobalVars.pipeline_frozen) {
        //    return;
        //}

        // Next stage hasn't consumed it or data not available
        if (rf.production[produce]!=null || rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];
        instruction.operands.getRenames();
        if (!instruction.operands.readyToIssue()) {
            return;
        }
        instruction.setStatus(InstructionStatus.Waiting);

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
            System.out.printf("[D2]:%s %s; ", instruction.getInstr().toString(), instruction.operands.toString());
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
