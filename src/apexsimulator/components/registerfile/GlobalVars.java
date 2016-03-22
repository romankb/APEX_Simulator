/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

/**
 * Class with static variables that serve as global
 * constants and variable for datapath internal usage
 *
 * @author Roman Kurbanov
 */
public class GlobalVars {
    public static final int INSTR_START = 20000;
    public static boolean execution_completed = false;
    public static boolean pipeline_frozen = false;
    public static final int PHYS_REG_COUNT = 16;
}
