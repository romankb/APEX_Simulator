/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.instructions;

import apexsimulator.components.Operand;
import apexsimulator.util.InstructionStatus;
import apexsimulator.util.InstructionsEnum;

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

    private InstructionsEnum type;
    private InstructionStatus status;
    private int PC;

    /**
     * Minimal version. Everything is initialized in stages
     */
    public Instruction() {
        status = InstructionStatus.Raw;
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


}
