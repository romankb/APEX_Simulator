/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.memory;

/**
 * Interface that fetches instructions at particular address
 *
 * @author Roman Kurbanov
 */
public interface MemoryInterface {

    /**
     * Returns text representation of instruction at particular address
     * halts the execution if wrong address is passed
     * @param address addreess of instruction
     * @return Instruction at a particular address
     */
    String fetch(int address);

    /**
     * Returns data read from memory
     * @param address address of memory area
     * @return integer value stored in memory
     */
    int readData(int address);

    /**
     * Purges existing data in d-cache and reloads instructions
     * in i-cache
     */
    void reload();

    /**
     * Sets filename to read instructions from
     * @param fileName filename with instructions
     */
    void setFileName(String fileName);

    /**
     * Display contents of first 100 memory locations
     */
    void display();
}
