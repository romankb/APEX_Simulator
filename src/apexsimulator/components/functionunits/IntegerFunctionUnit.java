/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

import apexsimulator.components.instructions.Instruction;

/**
 * This class implements integer function unit for instructions
 * ADD, SUB, MOVC, AND, OR, EX-OR
 * BZ, BNZ, JUMP, BAL, HALT
 *
 * @author Roman Kurbanov
 */
public class IntegerFunctionUnit implements FunctionUnitInterface{

    Instruction instruction;
    boolean ready;

    public IntegerFunctionUnit() {
        ready = true;
    }


    @Override
    public boolean ready() {
        return false;
    }

    @Override
    public void nextCycle() {

    }
}
