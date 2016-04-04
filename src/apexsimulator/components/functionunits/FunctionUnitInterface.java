/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.functionunits;

/**
 * This class defines public interface for all function units
 *
 * @author Roman Kurbanov
 */
public interface FunctionUnitInterface {
    boolean ready();
    void nextCycle();
}
