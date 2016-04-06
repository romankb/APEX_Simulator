/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;


/**
 * Interface for a typical stage in the pipeline
 *
 * @author Roman Kurbanov
 */
public interface StageInterface {

    /**
     * Clock cycle received
     */
    void nextCycle();

    /**
     * Clears stage
     */
    void clear();

    /**
     * Displays stage
     */
    void display();

    /**
     * Id of stage
     * @param id unique number of the stage
     */
    void setId(int id);
}
