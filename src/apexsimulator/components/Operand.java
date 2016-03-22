/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.util.ErrorCodes;

/**
 * This class defines operand type for instruction
 *
 * @author Roman Kurbanov
 */
public class Operand {
    private boolean register;
    private boolean valid;
    private boolean used;       // If is relevant for instruction
    private int value;
    private String oper;


    public Operand() {
        register = false;
        valid = false;
        used = false;
        value = 0;
        oper = "";
    }

    // getters and setters

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getValue() {
        if (used==false) {
            System.out.println("Error. Wrong usage of instruction");
            System.exit(ErrorCodes.ACCESS_ERROR);
        }
        return value;
    }

    public void setValue(int value) {
        if (used==false) {
            System.out.println("Error. Wrong usage of instruction");
            System.exit(ErrorCodes.ACCESS_ERROR);
        }
        this.value = value;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }
}
