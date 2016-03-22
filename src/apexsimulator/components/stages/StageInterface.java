/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.Instruction;

/**
 * Interface for a typical stage in the pipeline
 *
 * @author Roman Kurbanov
 */
public interface StageInterface {

    /**
     * Clock cycle received
     * @param instruction instruction to be operated on
     */
    void advance(Instruction instruction);
}
