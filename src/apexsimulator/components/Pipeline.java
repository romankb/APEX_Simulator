/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.components.stages.*;

/**
 * This class contains all the stages, latches and common clock
 *
 * @author Roman Kurbanov
 */
public class Pipeline implements DisplayInterface{

    private StageInterface[] stages;


    private RegisterFile rf;

    public Pipeline() {

        rf = RegisterFile.getInstance();
        stages = new StageInterface[5];
        stages[0] = new Fetch1();
        stages[1] = new Fetch2();
        stages[2] = new Decode1();
        stages[3] = new Decode2();
        stages[4] = new Execution();

        for (int i = 0; i < 5; ++i) {
            stages[i].setId(i);
        }


    }

    public void reload() {
        for (int i = 0; i < 5; ++i) {
            stages[i].clear();
        }
    }

    public void nextCycle() {
        for (int i = 4; i >=0; --i) {
            stages[i].nextCycle();
        }
    }

    /**
     * Prints status to console
     */
    @Override
    public void display() {
        System.out.println("---Contents of stages in the pipeline:---");
        for (int i = 0; i < 5; ++i) {
            stages[i].display();
        }
        System.out.println();
        System.out.println("-----------------------------------------");
    }
}
