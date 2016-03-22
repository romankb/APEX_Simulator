/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

/**
 * This class defines constants for typical errors during
 * emulator runtime. List can be extended.
 *
 * @author Roman Kurbanov
 */
public class ErrorCodes {

    public static final int USAGE_ERROR = 1;
    public static final int FILE_ERROR = 2;
    public static final int CONSOLE_ERROR = 3;
    public static final int SEGFAULT_ERROR = 4;
    public static final int DECODE_ERROR = 5;
    public static final int ACCESS_ERROR = 6;

    // preventing instantiation of the class
    private ErrorCodes(){}
}
