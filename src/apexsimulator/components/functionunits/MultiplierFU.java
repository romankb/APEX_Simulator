/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

import apexsimulator.components.Queues;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;

/**
 * Multiplier Function Unit
 *
 * @author Roman Kurbanov
 */
public class MultiplierFU {
    public boolean available;

    private Instruction curInstruction;
    private int cycle;

    private Queues queues;

    public MultiplierFU(Queues queues) {
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
        else if (cycle == 3)    cycle3();

    }

    private void cycle0() {
        curInstruction = queues.getVfu3Instruction();
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
        cycle++;
    }

    private void cycle3() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] * curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

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
            System.out.printf("MulFU[EMPTY]; ");
            return;
        }
        System.out.printf("MulFU[%s %s]; ", curInstruction.getInstr(), curInstruction.operands.opsToString());
    }

}
