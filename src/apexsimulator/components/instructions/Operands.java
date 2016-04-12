/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.instructions;

import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.Register;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.util.ArchRegisterEnum;
import apexsimulator.util.ErrorCodes;
import apexsimulator.util.InstructionsEnum;

import java.util.regex.Pattern;

/**
 * Class that will hold tags, values
 * and operands for an instruction
 * Number of them will depend on instruction type
 *
 * @author Roman Kurbanov
 */
public class Operands {
    public int[] ops;
    public ArchRegisterEnum[] regNames;
    public Register[] output;
    public Register[] waiting;




    private String[] tokens;

    private int regCount;

    private InstructionsEnum type;

    /**
     * Sets of overloaded constructors for different type of arguments
     *
     * @param tokens string tokens
     * @param type type of instruction
     */
    public Operands(String[] tokens, InstructionsEnum type) {
        this.type = type;
        this.tokens = tokens;
        regCount = 0;
        if (!checkNumArgs()) {
            System.out.println("Error. Wrong number of arguments for instruction: \""+type+"\"");
            System.out.println("Terminating execution");
            System.exit(ErrorCodes.DECODE_ERROR);
        }
        tryToPopulate();

    }

    private void tryToPopulate() {
        // convention for myself if has dest it is first in regnames wait before out
        if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            waiting = new Register[1];
            waiting[0] = RegisterFile.getInstance().rat.getLatest(ArchRegisterEnum.Z);

            regNames = new ArchRegisterEnum[1];
            regNames[0] = ArchRegisterEnum.Z;

            ops = new int[2];
            ops[0] = parseVal(tokens[1]);
            // ops[1] is waited

            output = new Register[0];
        } else if (type == InstructionsEnum.HALT) {
            //if(!RegisterFile.getInstance().branching)    GlobalVars.pipeline_frozen = true;

            regNames = new ArchRegisterEnum[0];
            waiting = new Register[0];
            ops = new int[0];
            output = new Register[0];

        } else if (type == InstructionsEnum.BAL){
            ops = new int[2];
            ops[1] = parseVal(tokens[2]);
            //ops[0] waited

            regNames = new ArchRegisterEnum[2];
            regNames[0] = ArchRegisterEnum.X;
            regNames[1] = parseReg(tokens[1]);

            waiting = new Register[1];
            waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[1]);

            output = new Register[1];
            output[0] = RegisterFile.getInstance().rat.assignFree(regNames[0]);


        } else if (type == InstructionsEnum.JUMP) {
            ops = new int[2];
            ops[1] = parseVal(tokens[2]);
            //ops[0] waited

            regNames = new ArchRegisterEnum[1];
            regNames[0] = parseReg(tokens[1]);

            output = new Register[0];

            waiting = new Register[1];
            waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[0]);
        } else if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR) {

            ops = new int[2];
            // no literals to populate moving forward

            regNames = new ArchRegisterEnum[4];
            regNames[0] = parseReg(tokens[1]);
            regNames[1] = ArchRegisterEnum.Z;
            regNames[2] = parseReg(tokens[2]);
            regNames[3] = parseReg(tokens[3]);

            waiting = new Register[2];
            waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[2]);
            waiting[1] = RegisterFile.getInstance().rat.getLatest(regNames[3]);

            // dst and zflag
            output = new Register[2];
            output[0] = RegisterFile.getInstance().rat.assignFree(regNames[0]);
            output[1] = RegisterFile.getInstance().rat.assignFree(regNames[1]);


        } else if (type == InstructionsEnum.MOVC) {
            ops = new int[1];
            if (Pattern.matches("^[a-zA-Z].*", tokens[2])) {
                regNames = new ArchRegisterEnum[3];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = ArchRegisterEnum.Z;
                regNames[2] = parseReg(tokens[2]);

                waiting = new Register[1];
                waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[2]);

            }else{
                ops[0] = parseVal(tokens[2]);
                regNames = new ArchRegisterEnum[2];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = ArchRegisterEnum.Z;

                waiting = new Register[0];

            }






            // dst and zflag
            output = new Register[2];
            output[0] = RegisterFile.getInstance().rat.assignFree(regNames[0]);
            output[1] = RegisterFile.getInstance().rat.assignFree(regNames[1]);



        } else if (type == InstructionsEnum.LOAD) {
            ops = new int[2];
            if (Pattern.matches("^[a-zA-Z].*", tokens[3])) {
                regNames = new ArchRegisterEnum[3];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = parseReg(tokens[2]);
                regNames[2] = parseReg(tokens[3]);

                waiting = new Register[2];
                waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[1]);
                waiting[1] = RegisterFile.getInstance().rat.getLatest(regNames[2]);

                output = new Register[1];
                output[0] = RegisterFile.getInstance().rat.assignFree(regNames[0]);


            } else {
                ops[1] = parseVal(tokens[3]);

                regNames = new ArchRegisterEnum[2];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = parseReg(tokens[2]);

                waiting = new Register[1];
                waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[1]);

                output = new Register[1];
                output[0] = RegisterFile.getInstance().rat.assignFree(regNames[0]);


            }

        } else {
            ops = new int[3];
            if (Pattern.matches("^[a-zA-Z].*", tokens[3])) {
                regNames = new ArchRegisterEnum[3];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = parseReg(tokens[2]);
                regNames[2] = parseReg(tokens[3]);

                output = new Register[0];

                waiting = new Register[3];
                waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[0]);
                waiting[1] = RegisterFile.getInstance().rat.getLatest(regNames[1]);
                waiting[2] = RegisterFile.getInstance().rat.getLatest(regNames[2]);
            } else {
                ops[2] = parseVal(tokens[3]);

                regNames = new ArchRegisterEnum[2];
                regNames[0] = parseReg(tokens[1]);
                regNames[1] = parseReg(tokens[2]);

                output = new Register[0];

                waiting = new Register[2];
                waiting[0] = RegisterFile.getInstance().rat.getLatest(regNames[0]);
                waiting[1] = RegisterFile.getInstance().rat.getLatest(regNames[1]);
            }
        }


    }

    @Override
    public String toString() {
        String res = "";
        // convention for myself if has dest it is first in regnames
        if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            res = tokens[1];
        } else if (type == InstructionsEnum.BAL || type == InstructionsEnum.JUMP){
            res = String.format("%s %s", tokens[1], tokens[2]);

        } else if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR ||
                type == InstructionsEnum.LOAD) {
            if (output[0] == null) {
                res = String.format("%s %s %s", tokens[1], tokens[2], tokens[3]);
            } else {
                res = String.format("P%d %s %s", output[0].physId, tokens[2], tokens[3]);
            }

        } else if (type == InstructionsEnum.MOVC) {
            if (output[0]==null) {
                res = tokens[1] + " " + tokens[2];
            } else {
                res = "P" + output[0].physId + " " + tokens[2];
            }

        } else  if (type == InstructionsEnum.STORE){
            res = String.format("%s %s %s", tokens[1], tokens[2], tokens[3]);
        } else {
            // do nothing
        }


        return res;
    }

    /**
     * Prints values themselves not register names
     * @return formatted string with data
     */
    public String opsToString() {
        String res = "";
        // convention for myself if has dest it is first in regnames
        if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            res = tokens[1];
        } else if (type == InstructionsEnum.BAL || type == InstructionsEnum.JUMP){
            res = String.format("%d %d", ops[0], ops[1]);

        } else if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR ||
                type == InstructionsEnum.LOAD) {
                res = String.format("P%d %s %s", output[0].physId, ops[0], ops[1]);

        } else if (type == InstructionsEnum.MOVC) {
                res = "P" + output[0].physId + " " + ops[0];
        } else  if (type == InstructionsEnum.STORE){
            res = String.format("%s %s %s", ops[0], ops[1], ops[2]);
        } else {
            // do nothing
        }


        return res;
    }

    // only called if ready to dispatch
    public void populateOps() {
        if (type == InstructionsEnum.NOP)   return;

        if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            ops[1] = waiting[0].getValue();
        } else if (type == InstructionsEnum.BAL || type == InstructionsEnum.JUMP) {
            ops[0] = waiting[0].getValue();
        } else if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR) {
            ops[0] = waiting[0].getValue();
            ops[1] = waiting[1].getValue();
        } else if (type == InstructionsEnum.LOAD || type == InstructionsEnum.STORE || type == InstructionsEnum.MOVC) {
            for (int i=0; i < waiting.length; ++i) {
                ops[i] = waiting[i].getValue();
            }
        } else {
            return;
        }
    }

    // no worries releasing renames is safe doesn't affect commits
    public void releasePhysReg() {
        for (int i = 0; i < output.length; ++i) {
            if (output[i]!=null) {
                RegisterFile.getInstance().rat.deleteRename(regNames[i], output[i]);
                output[i].reload();
                output[i] = null;
            }
        }
    }

    /**
     * Checks if all values were received and instruction is ready for execution
     * @return true is all operands are ready, false otherwise
     */
    public boolean readyToDispatch() {
        if (type == InstructionsEnum.NOP)   return true;
        boolean ready = true;
        for (int i = 0; i < waiting.length; ++i) {
            if (waiting[i] == null) {
                System.out.println("Error. Operation relies on unitialized register " + type);
                System.out.println("Terminating execution");
                System.exit(ErrorCodes.ACCESS_ERROR);
            } else if (waiting[i].isValid() == false) {
                return false;
            }
        }
        return ready;
    }

    // tries to acquire renamed registers again
    public void getRenames() {
        if (type == InstructionsEnum.NOP)   return;
        for (int i = 0; i < output.length; ++i) {
            if (output[i] == null) {
                output[i] = RegisterFile.getInstance().rat.assignFree(regNames[i]);
            }
        }
    }

    /**
     * Checks if instruction is ready to be added to issue queue
     * @return false if renames weren't acquired
     */
    public boolean readyToIssue () {
        if (type == InstructionsEnum.NOP)   return true;
        boolean ready = true;
        for (int i = 0; i < output.length; ++i) {
            if (output[i] == null) {
                return false;
            }
        }
        return ready;
    }

    // check number of args
    private boolean checkNumArgs() {
        if (type == InstructionsEnum.ADD || type == InstructionsEnum.SUB || type == InstructionsEnum.MUL ||
                type == InstructionsEnum.AND || type == InstructionsEnum.OR || type == InstructionsEnum.EX_OR ||
                type == InstructionsEnum.LOAD || type == InstructionsEnum.STORE) {
            if (tokens.length != 4) return false;
            //instruction.operands = new Operands(tokens[1], tokens[2], tokens[3], type);
        } else if (type == InstructionsEnum.MOVC || type == InstructionsEnum.BAL || type == InstructionsEnum.JUMP) {
            if (tokens.length != 3) return false;
            //instruction.operands = new Operands(tokens[1], tokens[2], type);
        } else if (type == InstructionsEnum.BZ || type == InstructionsEnum.BNZ) {
            if (tokens.length != 2) return false;
            //instruction.operands = new Operands(tokens[1], type);
        } else if (type == InstructionsEnum.HALT) {
            if (tokens.length != 1) return false;
            //instruction.operands = new Operands(type);
        } else {
            return false;
        }
        return true;
    }

    private int parseVal(String text) {
        int result = 0;
        try {
            result =  Integer.parseInt(text);
        } catch (Exception e) {
            System.out.println("Error. Wrong literal value " + text);
            System.out.println("Terminating execution");
            //System.out.println(type);
            System.exit(ErrorCodes.DECODE_ERROR);
        }
        return result;
    }


    private ArchRegisterEnum parseReg(String text) {
        text = text.trim();
        text = text.toUpperCase();
        ArchRegisterEnum result = ArchRegisterEnum.R0;
        try {
            result = ArchRegisterEnum.valueOf(text);
        } catch (Exception e) {
            System.out.println("Error. Wrong register name " + text);
            System.out.println("Terminating execution");
            System.exit(ErrorCodes.DECODE_ERROR);
        }
        return result;
    }

    /**
     * Releases all acquired resources and renames of squashed instruction
     */
    public void reset() {
        if (type == InstructionsEnum.HALT) {
            GlobalVars.pipeline_frozen = false;
        }

        this.type = InstructionsEnum.NOP;
        releasePhysReg();
    }
}
