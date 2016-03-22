/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

/**
 * This interface defines methods for the program loader.
 * Different implementations can exist.
 *
 * @author Roman Kurbanov
 */
public interface LoaderInterface {

    /**
     * Attempts to load instructions into memory
     */
    void loadProgram();
}
