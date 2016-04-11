/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.util.InstructionsEnum;

import java.util.LinkedList;

/**
 * Issue queue and load/store queue
 *
 * @author Roman Kurbanov
 */
public class Queues {

    private LinkedList<Instruction> iq;
    private LinkedList<Instruction> lsq;

    public Queues() {
        iq = new LinkedList<>();
        lsq = new LinkedList<>();
    }

    public boolean fullIq() {
        return iq.size() == GlobalVars.IQ_SIZE;
    }

    public boolean fullLsq() {
        return lsq.size() == GlobalVars.LSQ_SIZE;
    }

    public boolean canAdd(Instruction instrIn) {
        boolean res = true;

        if (instrIn.getInstr() == InstructionsEnum.LOAD || instrIn.getInstr() == InstructionsEnum.STORE) {
            if (fullLsq()) res = false;
        } else {
            if (fullIq())   res = false;
        }


        return res;
    }

    public void addInstruction(Instruction instrIn) {
        if (instrIn.getInstr() == InstructionsEnum.LOAD || instrIn.getInstr() == InstructionsEnum.STORE) {
            lsq.addLast(instrIn);
        } else {
            iq.addLast(instrIn);
        }
    }

    /**
     * clears the queues
     */
    public void reload() {
        iq.clear();
        lsq.clear();
    }

    /**
     * output contents to the console
     */
    public void display() {
        System.out.print("IQ:( ");
        for (Instruction temp:
                iq) {
            System.out.printf("[%s %s]; ", temp.getInstr(), temp.getStatus());
        }
        System.out.println(" )");
        System.out.print("LSQ:( ");

        for (Instruction temp:
                lsq) {
            System.out.printf("[%s %s]; ", temp.getInstr(), temp.getStatus());
        }
        System.out.println(" )");
    }


    public Instruction getVfu0Instruction () {
        if (iq.size()!= 0) {
            for (Instruction temp : iq) {
                if (temp.getInstr() == InstructionsEnum.HALT || temp.getInstr() == InstructionsEnum.NOP) {
                    iq.remove(temp);
                    return temp;
                }
            }
        }

        if (lsq.size()!= 0) {
            for (Instruction temp : lsq) {
                if (temp.getInstr() == InstructionsEnum.NOP) {
                    lsq.remove(temp);
                    return temp;
                }
            }
        }
        return null;
    }

    public Instruction getVfu1Instruction() {
        if (lsq.size() != 0) {
            if (lsq.peekFirst().operands.readyToDispatch()) {
                return lsq.pop();
            }
        }
        return null;
    }
    public Instruction getVfu2Instruction() {
        if (iq.size() != 0) {
            for (Instruction temp:iq) {
                if (temp.operands.readyToDispatch()) {
                    if (temp.getInstr() == InstructionsEnum.ADD || temp.getInstr() == InstructionsEnum.SUB ||
                            temp.getInstr() == InstructionsEnum.MOVC || temp.getInstr() == InstructionsEnum.AND ||
                            temp.getInstr() == InstructionsEnum.OR || temp.getInstr() == InstructionsEnum.EX_OR ||
                            temp.getInstr() == InstructionsEnum.BZ || temp.getInstr() == InstructionsEnum.BNZ ||
                            temp.getInstr() == InstructionsEnum.JUMP || temp.getInstr() == InstructionsEnum.BAL) {
                        iq.remove(temp);
                        return temp;

                    }
                }
            }
        }
        return null;
    }

    public Instruction getVfu3Instruction() {
        if (iq.size() != 0) {
            for (Instruction temp:iq) {
                if (temp.getInstr() == InstructionsEnum.MUL && temp.operands.readyToDispatch()) {
                    iq.remove(temp);
                    return temp;
                }
            }
        }
        return null;
    }
}
