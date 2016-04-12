/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */


package apexsimulator.components;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.memory.Memory;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

    /**
     * Commit latest instruction at the head
     */
    public void retire() {
        if (empty())    return;

        if (robBuffer.getFirst().getStatus() == InstructionStatus.Completed) {
            Instruction temp = robBuffer.pop();
            if (temp.getInstr() == InstructionsEnum.STORE) {
                Memory.getInstance().writeMem((temp.operands.ops[1] + temp.operands.ops[2]), temp.operands.ops[0]);
            } else if (temp.getInstr() == InstructionsEnum.HALT) {
                GlobalVars.execution_completed = true;
            } else if (temp.getInstr() == InstructionsEnum.NOP) {

            } else {
                for (int i = 0; i < temp.operands.output.length; ++i) {
                    RegisterFile.getInstance().rat.commit(temp.operands.regNames[i], temp.operands.output[i]);
                }
            }
            RegisterFile.getInstance().setCommitedPC(temp.getPC());
        }

    }

    /**
     * In case of misprediction or unconditional branching, remove wrongly dispatched items
     *
     * @param brIn branching instruction
     */
    public void revert(Instruction brIn) {
        int ind = robBuffer.indexOf(brIn);
        ListIterator<Instruction> it = robBuffer.listIterator(ind);
        Instruction temp = it.next();
        while (it.hasNext()) {
            temp = it.next();

            temp.setInstr(InstructionsEnum.NOP);
            temp.operands.reset();
            temp.setStatus(InstructionStatus.Completed);
        }
    }

    /**
     * clears the rob
     */
    public void reload() {
        robBuffer.clear();
    }

    /**
     * output contents to the console
     */
    public void display() {
        System.out.print("ROB:( ");
        for (Instruction temp:
             robBuffer) {
            if (temp.getStatus()!=InstructionStatus.Completed && temp.getStatus()!=InstructionStatus.Executing) {
                if (temp.operands.readyToDispatch()) {
                    temp.setStatus(InstructionStatus.Ready);
                }
            }
            System.out.printf("[%s %s]; ", temp.getInstr(), temp.getStatus());
        }
        System.out.println(" )");
    }
}
