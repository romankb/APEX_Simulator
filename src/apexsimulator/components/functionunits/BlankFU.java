/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

import apexsimulator.components.Queues;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.util.InstructionStatus;

/**
 * Function unit for NOPs and HALT
 *
 * @author Roman Kurbanov
 */
public class BlankFU {

    private Queues queues;


    public BlankFU(Queues queues) {
        this.queues = queues;
    }

    public void nextCycle() {
        Instruction temp = queues.getVfu0Instruction();
        if (temp != null) {
            temp.setStatus(InstructionStatus.Completed);
        }
    }

}
