/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.components.memory.Memory;
import apexsimulator.components.memory.MemoryInterface;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * This class combines all the simulated components into a working
 * CPU datapath
 *
 * @author Roman Kurbanov
 */
public class Datapath implements DatapathInterface{

    private MemoryInterface memory;
    private RegisterFile rf;
    private Pipeline pipeline;

    public Datapath() {
        memory = Memory.getInstance();
        rf = RegisterFile.getInstance();
        pipeline = new Pipeline();
    }

    /**
     * Initializes the simulator state, sets the PC of the fetch stage to
     * point to the first instruction. Reloads current execution.
     */
    @Override
    public void initialize() {
        memory.reload();
        pipeline.reload();
        rf.reload();
    }

    /**
     * simulates the number of cycles specified as "n" and waits.
     * Simulation can stop earlier if a HALT instruction is encountered
     * and when the HALT instruction is in the WB stage.
     *
     * @param n number of cycles
     */
    @Override
    public void simulate(int n) {
        for (int i=0; i<n; ++i) {
            pipeline.nextCycle();
            if (GlobalVars.execution_completed) {
                display();
                System.out.println("Execution completed successfully.");
                System.out.println("Good bye.");
                System.exit(0);
            }
        }
    }

    /**
     * Displays the contents of each stage in the pipeline and the contents
     * of the first 100 memory locations containing data, starting with address 0.
     * displays the contents of the architectural and physical registers, what is
     * inside each FU, the rename table, the IQ and ROB, the free list of
     * registers and all structures relevant to renaming, including the tag and
     * result value broadcasted etc
     */
    @Override
    public void display() {
        pipeline.display();
        memory.display();
        rf.display();
    }
}
