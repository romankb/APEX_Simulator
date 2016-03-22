/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

/**
 * This enum defines all the supported instructions
 * List can be extended without affecting existing functionality.
 *
 * @author Roman Kurbanov
 */
public enum InstructionsEnum {
    ADD,
    SUB,
    MOVC,
    MUL,
    AND,
    OR,
    EX_OR,
    LOAD,
    STORE,
    BZ,
    BNZ,
    JUMP,
    BAL,
    HALT,
    NOP,
    WRONG
}
