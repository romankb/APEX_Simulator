/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;
import apexsimulator.util.StringParser;

/**
 * This class defines instruction type and its common characteristics
 *
 * Fields will be updated as the instruction moves in the pipeline
 *
 * @author Roman Kurbanov
 */
public class Instruction {

    // Members that define typical instruction
    private String instruction;

    private Operand operand1;
    private Operand operand2;
    private Operand operand3;

    private InstructionsEnum type;
    private InstructionStatus status;
    private int PC;

    /**
     * Minimal version. Everything is initialized in stages
     */
    public Instruction() {
        operand1 = new Operand();
        operand2 = new Operand();
        operand3 = new Operand();
    }

    // Getters and setters
    public InstructionsEnum getInstr() {
        return type;
    }
    public void setInstr(InstructionsEnum instr) {
        this.type = instr;
    }

    public InstructionStatus getStatus() {
        return status;
    }
    public void setStatus(InstructionStatus status) {
        this.status = status;
    }

    public int getPC() {
        return PC;
    }
    public void setPC(int PC) {
        this.PC = PC;
    }

    /**
     * Getter for instruction field
     * @return string version of instruction
     */
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Operand getOperand1() {
        return operand1;
    }

    public void setOperand1(Operand operand1) {
        this.operand1 = operand1;
    }

    public Operand getOperand2() {
        return operand2;
    }

    public void setOperand2(Operand operand2) {
        this.operand2 = operand2;
    }

    public Operand getOperand3() {
        return operand3;
    }

    public void setOperand3(Operand operand3) {
        this.operand3 = operand3;
    }
}
