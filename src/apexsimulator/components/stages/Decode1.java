/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.Predictor;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.instructions.Operands;
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
    private int produce;
    private int consume;

    private String[] tokens;

    public Decode1() {
        rf = RegisterFile.getInstance();
    }


    /**
     * Clock cycle received
     */
    @Override
    public void nextCycle() {
        instruction = null;
        // end of operations
        if (GlobalVars.pipeline_frozen) {
            return;
        }

        // Next stage hasn't consumed it or data not available
        if (rf.production[produce]!=null || rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];

        // splitting text into tokens

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

        // setting instruction type and checking number of arguments
        instruction.setInstr(type);
        instruction.operands = new Operands(tokens, type);

        if ( type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ ) {
            int offset = instruction.operands.ops[0];
            instruction.prediction = Predictor.predict(offset);

            if (instruction.prediction) {
                rf.setFetchPC(instruction.getPC()+offset);

                rf.production[0] = null;
                // need to work and debug
            }
        }
        if (type == InstructionsEnum.HALT) {
            GlobalVars.pipeline_frozen = true;
            rf.production[0] = null;
        }


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
