/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

/**
 * The {@code FileInterface} defines core API needed by software for file
 * operations
 *
 * @author Roman Kurbanov
 */
public interface FileInterface {
    /**
     * Reads one line at a time from the file
     * @return read out string or null if nothing to read
     */
    String readLine();

    /**
     * Tries to open file stream
     */
    void openFile();

    /**
     * Tries to close file streem
     */
    void closeFile();
}
