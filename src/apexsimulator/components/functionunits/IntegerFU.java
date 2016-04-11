/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

import apexsimulator.components.Queues;
import apexsimulator.components.ROB;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;

/**
 * Integer function unit for instructions
 * ADD, SUB, MOVC, AND, OR, EX-OR
 * BZ, BNZ, JUMP, BAL
 *
 * @author Roman Kurbanov
 */
public class IntegerFU {

    public boolean available;

    private Instruction curInstruction;
    private Queues queues;
    private ROB rob;

    public IntegerFU(Queues queues, ROB rob) {
        this.queues = queues;
        this.rob = rob;
        reload();
    }

    public void nextCycle() {
        if (available) {
            curInstruction = queues.getVfu2Instruction();
            if (curInstruction != null){
                available = false;
                curInstruction.operands.populateOps();
                curInstruction.setStatus(InstructionStatus.Executing);
                complete();
            }
        } else {
            complete();
        }
    }




    private void complete() {

        switch (curInstruction.getInstr()){

            case ADD:   completeAdd();
                break;
            case SUB:   completeSub();
                break;
            case MOVC:  completeMovc();
                break;
            case AND:   completeAnd();
                break;
            case OR:    completeOr();
                break;
            case EX_OR: completeXor();
                break;
            case BZ:    completeBz();
                break;
            case BNZ:   completeBnz();
                break;
            case JUMP:  completeJump();
                break;
            case BAL:   completeBal();
                break;
            default:    completeNop();
                break;
        }

    }

    private void completeJump() {
        RegisterFile.getInstance().setFetchPC(curInstruction.operands.ops[0]+curInstruction.operands.ops[1]);
        rob.revert(curInstruction);
        RegisterFile.getInstance().rat.failure();
        RegisterFile.getInstance().clearLatches();
        RegisterFile.getInstance().branching = false;



        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }

    private void completeBal() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        RegisterFile.getInstance().setFetchPC(curInstruction.operands.ops[0]+curInstruction.operands.ops[1]);
        curInstruction.operands.output[0].setValue(curInstruction.getPC()+1);
        curInstruction.operands.output[0].setValid(true);
        rob.revert(curInstruction);
        RegisterFile.getInstance().rat.failure();
        RegisterFile.getInstance().clearLatches();
        RegisterFile.getInstance().branching = false;
        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }

    private void completeBnz() {
        if (curInstruction.operands.ops[1] != 0) {
            if (RegisterFile.getInstance().prediction == false) {

                RegisterFile.getInstance().setFetchPC(curInstruction.getPC()+curInstruction.operands.ops[0]);
                rob.revert(curInstruction);
                RegisterFile.getInstance().rat.failure();
                RegisterFile.getInstance().clearLatches();
            } else {
                RegisterFile.getInstance().rat.success();
            }
        } else {
            if (RegisterFile.getInstance().prediction == true) {
                RegisterFile.getInstance().setFetchPC(curInstruction.getPC()+1);
                rob.revert(curInstruction);
                RegisterFile.getInstance().rat.failure();
                RegisterFile.getInstance().clearLatches();
            } else {
                RegisterFile.getInstance().rat.success();
            }
        }
        RegisterFile.getInstance().branching = false;
        RegisterFile.getInstance().prediction = false;
        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }

    private void completeBz() {
        if (curInstruction.operands.ops[1] == 0) {
            if (RegisterFile.getInstance().prediction == false) {

                RegisterFile.getInstance().setFetchPC(curInstruction.getPC()+curInstruction.operands.ops[0]);
                rob.revert(curInstruction);
                RegisterFile.getInstance().rat.failure();
                RegisterFile.getInstance().clearLatches();
            } else {
                RegisterFile.getInstance().rat.success();
            }
        } else {
            if (RegisterFile.getInstance().prediction == true) {
                RegisterFile.getInstance().setFetchPC(curInstruction.getPC()+1);
                rob.revert(curInstruction);
                RegisterFile.getInstance().rat.failure();
                RegisterFile.getInstance().clearLatches();
            } else {
                RegisterFile.getInstance().rat.success();
            }
        }
        RegisterFile.getInstance().branching = false;
        RegisterFile.getInstance().prediction = false;
        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }

    private void completeNop() {
        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeAdd() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] + curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeSub() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] - curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeAnd() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] & curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeOr() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] | curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeXor() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        int res = curInstruction.operands.ops[0] ^ curInstruction.operands.ops[1];
        curInstruction.operands.output[0].setValue(res);
        curInstruction.operands.output[0].setValid(true);

        res = ((res==0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(res);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }
    private void completeMovc() {
        if (!RegisterFile.getInstance().forwardingAvailable)    return;
        curInstruction.operands.output[0].setValue(curInstruction.operands.ops[0]);
        curInstruction.operands.output[0].setValid(true);
        int temp = ((curInstruction.operands.ops[0] == 0) ? 0 : 1);
        curInstruction.operands.output[1].setValue(temp);
        curInstruction.operands.output[1].setValid(true);

        available = true;
        curInstruction.setStatus(InstructionStatus.Completed);
    }

    public void reload() {
        available = true;
        curInstruction = null;
    }

    public void display () {
        if (curInstruction == null) {
            System.out.printf("IntFU[EMPTY]; ");
            return;
        }
        System.out.printf("IntFU[%s]; ", curInstruction.getInstruction().trim());
    }
}
