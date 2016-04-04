/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.registerfile;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.instructions.Instruction;

/**
 * Register file implemented as singleton
 *
 * Has wide variety of responsibilities, but generally
 * encapsulates everything that is needed for register
 * operation.
 *
 * Also manipulates global var variables
 *
 * @author Roman Kurbanov
 */
public class RegisterFile implements DisplayInterface {

    private int fetchPC;
    private int committedPC;

    private volatile static RegisterFile rf;
    public RAT rat;

    public Instruction[] production;

    private RegisterFile() {
        rat = new RAT();
        production = new Instruction[4];
        reload();


    }

    /**
     * creates or returns previously created unique instance
     * of the class
     * this method is thread safe
     *
     * @return unique instance of {@code Memory} class
     */
    public static RegisterFile getInstance(){
        if (rf == null) {
            synchronized(RegisterFile.class) {
                if (rf == null) {
                    rf = new RegisterFile();
                }
            }
        }
        return rf;
    }

    public void reload() {
        fetchPC = GlobalVars.INSTR_START;
        committedPC = -1;
        GlobalVars.execution_completed = false;
        GlobalVars.pipeline_frozen = false;
        rat.reload();
        for (int i=0; i < 4; ++i) {
            production[i] = null;
        }
    }

    // Getters and setters

    public int getFetchPC() {
        return fetchPC;
    }

    public void setFetchPC(int fetchPC) {
        this.fetchPC = fetchPC;
    }

    public int getCommitedPC() {
        return committedPC;
    }

    public void setCommitedPC(int commitedPC) {
        this.committedPC = commitedPC;
    }

    /**
     * Prints contents of register file to console
     */
    @Override
    public void display() {
        System.out.println("---Contents of register file:---");
        rat.display();
        System.out.println("--------------------------------");
    }
}
