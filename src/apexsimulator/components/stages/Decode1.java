/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.ErrorCodes;
import apexsimulator.util.InstructionsEnum;
import apexsimulator.util.StringParser;

/**
 * Decode/Dispatch stage 1
 * Determines the type of instruction and checks if name is correct
 * and number of arguments is correct
 *
 * @author Roman Kurbanov
 */
public class Decode1 implements StageInterface{
    private Instruction instruction;
    private RegisterFile rf;
    //String[] tokens;
    private int produce;
    private int consume;

    public Decode1() {
        rf = RegisterFile.getInstance();
    }


    /**
     * Clock cycle received
     */
    @Override
    public void nextCycle() {
        // end of operations
        if (GlobalVars.pipeline_frozen) {
            return;
        }

        // Next stage hasn't consumed it or data not available
        if (rf.production[produce]!=null || rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];
        // do smth with instruction
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
/*    @Override
    public void advance(Instruction instruction) {
        this.instruction = instruction;
        if (instruction==null) return;
        tokens = instruction.getInstruction().split("\\s");
        if (tokens.length<1) {
            System.out.println("Error. Instruction \"" + instruction.getInstruction() + "\" cannot be decoded");
            System.out.println("Terminating execution");
            System.exit(ErrorCodes.DECODE_ERROR);
        }

        InstructionsEnum type = StringParser.getInstr(tokens[0]);
        if (type==InstructionsEnum.WRONG) {
            System.out.println("Error. Instruction \"" + instruction.getInstruction() + "\" cannot be decoded");
            System.out.println("Terminating execution");
            System.exit(ErrorCodes.DECODE_ERROR);
        }

        if (!checkAndPopulateArgs(type)) {
            System.out.println("Error. Wrong number of arguments for instruction: \""+instruction+"\"");
            System.out.println("Terminating execution");
            System.exit(ErrorCodes.DECODE_ERROR);
        }

    }

    // todo add zero register
    private boolean checkAndPopulateArgs(InstructionsEnum type) {
        if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR ||
                type == InstructionsEnum.LOAD || type == InstructionsEnum.STORE) {
            if (tokens.length != 4) return false;
            instruction.getOperand1().setUsed(true);
            instruction.getOperand1().setOper(tokens[1]);

            instruction.getOperand2().setUsed(true);
            instruction.getOperand2().setOper(tokens[2]);

            instruction.getOperand3().setUsed(true);
            instruction.getOperand3().setOper(tokens[3]);

        } else if (type == InstructionsEnum.MOVC || type == InstructionsEnum.BAL || type == InstructionsEnum.JUMP) {
            if (tokens.length != 3) return false;
            instruction.getOperand1().setUsed(true);
            instruction.getOperand1().setOper(tokens[1]);

            instruction.getOperand2().setUsed(true);
            instruction.getOperand2().setOper(tokens[2]);
        } else if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            if (tokens.length != 2) return false;
            instruction.getOperand1().setUsed(true);
            instruction.getOperand1().setOper(tokens[1]);
        } else if (type == InstructionsEnum.HALT) {
            if (tokens.length != 1) return false;
            RegisterFile.getInstance().pipelineFrozen = true;
        } else {
            return false;
        }
        return true;
    }*/

    /**
     * Prints status to console
     */
    @Override
    public void display() {
        if (instruction==null) {
            System.out.print("[D1]:EMPTY; ");
        } else {
            System.out.printf("[D1]:%s; ", instruction.getInstr().toString());
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
