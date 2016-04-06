/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;


/**
 * This class packs all the fields needed for register
 * <p>
 * No exceptions will be generated in case of wrong access
 * Also can be used as a flag
 *
 * @author Roman Kurbanov
 */
public class Register {
    private boolean valid;
    private int value;
    private boolean used;
    public int physId;

    /**
     * Creates default register which is not valid
     * and not used beforehand
     */
    public Register() {
        reload();
    }

    /**
     * Resets internal state to default
     */
    public void reload() {
        valid = false;
        value = 0;
        used = false;
        physId = -1;
    }

    // getters and setters

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
