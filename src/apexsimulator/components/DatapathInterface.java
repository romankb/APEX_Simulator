/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

/**
 * Interface for establishing communication between user and
 * control logic
 *
 * @author Roman Kurbanov
 */
public interface DatapathInterface {

    /**
     * Initializes the simulator state, sets the PC of the fetch stage to
     * point to the first instruction. Reloads current execution.
     */
    void initialize();

    /**
     * simulates the number of cycles specified as <n> and waits.
     * Simulation can stop earlier if a HALT instruction is encountered
     * and when the HALT instruction is in the WB stage.
     * @param n number of cycles
     */
    void simulate(int n);

    /**
     * Displays the contents of each stage in the pipeline and the contents
     * of the first 100 memory locations containing data, starting with address 0.
     * displays the contents of the architectural and physical registers, what is
     * inside each FU, the rename table, the IQ and ROB, the free list of
     * registers and all structures relevant to renaming, including the tag and
     * result value broadcasted etc
     */
    void display();
}
