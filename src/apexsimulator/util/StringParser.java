/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

/**
 * Has some static helper functions to deal with parsing strings.
 *
 * @author Roman Kurbanov
 */
public class StringParser {
    // No instances are allowed. It is here just to make sure noone forgets that.
    private StringParser() {}

    /**
     * Converts text representation to Instruction Enumerator
     * @param instr text
     * @return enumerator type
     */
    public static InstructionsEnum getInstr(String instr) {
        switch (instr.toUpperCase()) {
            case "ADD" :    return InstructionsEnum.ADD;
            case "SUB" :    return InstructionsEnum.SUB;
            case "MOVC" :   return InstructionsEnum.MOVC;
            case "MOV" :   return InstructionsEnum.MOVC;
            case "MUL" :    return InstructionsEnum.MUL;
            case "AND" :    return InstructionsEnum.AND;
            case "OR" :     return InstructionsEnum.OR;
            case "EX-OR" :  return InstructionsEnum.EX_OR;
            case "LOAD" :   return InstructionsEnum.LOAD;
            case "STORE" :  return InstructionsEnum.STORE;
            case "BZ" :     return InstructionsEnum.BZ;
            case "BNZ" :    return InstructionsEnum.BNZ;
            case "JUMP" :   return InstructionsEnum.JUMP;
            case "BAL" :    return InstructionsEnum.BAL;
            case "HALT" :   return InstructionsEnum.HALT;
            case "NOP" :    return InstructionsEnum.NOP;
        }
        return InstructionsEnum.WRONG;
    }

}
