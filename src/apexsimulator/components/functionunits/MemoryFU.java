/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

import apexsimulator.components.Queues;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.memory.Memory;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;

/**
 * Memory function unit
 *
 * @author Roman Kurbanov
 */
public class MemoryFU {

    public boolean available;

    private Instruction curInstruction;
    private int cycle;

    private Queues queues;

    public MemoryFU(Queues queues) {
        this.queues = queues;
        reload();
    }

    public void nextCycle() {

        if (curInstruction!=null && curInstruction.getInstr() == InstructionsEnum.NOP) {
            cycle = 0;
            available = true;
            curInstruction.setStatus(InstructionStatus.Completed);
            return;
        }

        if (cycle == 0)     cycle0();
        else if (cycle == 1)    cycle1();
        else if (cycle == 2)    cycle2();


    }

    private void cycle0() {
        curInstruction = queues.getVfu1Instruction();
        if (curInstruction != null){
            curInstruction.setStatus(InstructionStatus.Executing);
            cycle++;
            available = false;
        }
    }

    private void cycle1() {
        curInstruction.operands.populateOps();
        cycle++;

    }

    private void cycle2() {


        if (curInstruction.getInstr() == InstructionsEnum.LOAD) {
            if (!RegisterFile.getInstance().forwardingAvailable)    return;
            int addr = curInstruction.operands.ops[0] + curInstruction.operands.ops[1];
            curInstruction.operands.output[0].setValue(Memory.getInstance().readData(addr));
            curInstruction.operands.output[0].setValid(true);
        }
        cycle = 0;
        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);

    }

    public void reload() {
        available = true;
        curInstruction = null;
        cycle = 0;
    }

    public void display () {
        if (curInstruction == null) {
            System.out.printf("MemFU[EMPTY]; ");
            return;
        }
        System.out.printf("MemFU[%s %s]; ", curInstruction.getInstr(), curInstruction.operands.opsToString());
    }

}
