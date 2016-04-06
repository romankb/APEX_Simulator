/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

/**
 * Predictor class
 *
 * @author Roman Kurbanov
 */
public class Predictor {

    /**
     * Determines prediction based on offset
     *
     * @param offset integer value
     * @return prediction
     */
    public static boolean predict(int offset) {
        if (offset >= 0) {
            return false;
        }
        return true;
    }
}
