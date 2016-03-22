/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components;

import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;
import apexsimulator.components.stages.Decode1;
import apexsimulator.components.stages.Fetch1;
import apexsimulator.components.stages.Fetch2;

/**
 * This class contains all the stages, latches and common clock
 *
 * @author Roman Kurbanov
 */
public class Pipeline implements DisplayInterface{

    private Fetch1 fetch1;
    private Fetch2 fetch2;
    private Decode1 decode1;

    private Instruction latch1;
    private Instruction mlatch2;
    private Instruction slatch2;
    private Instruction mlatch3;
    private Instruction slatch3;
    private Instruction mlatch4;
    private Instruction slatch4;

    private RegisterFile rf;

    public Pipeline() {
        fetch1 = new Fetch1();
        fetch2 = new Fetch2();
        decode1 = new Decode1();
        rf = RegisterFile.getInstance();
    }

    public void reload() {
        latch1 = null;
        mlatch2 = null;
        slatch2 = null;
        mlatch3 = null;
        slatch3 = null;
        mlatch4 = null;
        slatch4 = null;
        fetch1.advance(null);
        fetch2.advance(null);
        decode1.advance(null);
    }

    public void nextCycle() {
        // doesn't make any sense
        if (GlobalVars.pipeline_frozen) {
            // decode2.advance(null);
            return;
        }
        latch1 = new Instruction();
        fetch1.advance(latch1);
        slatch2 = mlatch2;
        mlatch2 = latch1;
        fetch2.advance(slatch2);

        slatch3 = mlatch2;
        mlatch3 = slatch2;
        decode1.advance(slatch3);
        slatch4 = mlatch2;
        mlatch3 = slatch2;


    }

    /**
     * Prints status to console
     */
    @Override
    public void display() {
        System.out.println("---Contents of stages in the pipeline:---");
        ((DisplayInterface)fetch1).display();
        ((DisplayInterface)fetch2).display();
        System.out.println("-----------------------------------------");
    }
}
