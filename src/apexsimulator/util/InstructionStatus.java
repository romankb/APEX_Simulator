/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

/**
 * This enum for instruction statuses
 *
 * @author Roman Kurbanov
 */
public enum InstructionStatus {
    Raw,
    Waiting,
    Executing,
    Ready,
    Completed
}
