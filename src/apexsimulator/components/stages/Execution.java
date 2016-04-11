/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.stages;

import apexsimulator.components.Queues;
import apexsimulator.components.ROB;
import apexsimulator.components.functionunits.BlankFU;
import apexsimulator.components.functionunits.IntegerFU;
import apexsimulator.components.functionunits.MemoryFU;
import apexsimulator.components.functionunits.MultiplierFU;
import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.RegisterFile;


/**
 * Execution stage. Responsible for manipulating
 * issue and load/store queues as well ass reorder buffer
 *
 * @author Roman Kurbanov
 */
public class Execution implements StageInterface{

    Instruction instruction;
    RegisterFile rf;
    private int produce;
    private int consume;


    private ROB rob;
    private Queues queues;
    private BlankFU vfu0;
    private MemoryFU vfu1;
    private IntegerFU vfu2;
    private MultiplierFU vfu3;

    public Execution() {
        rf = RegisterFile.getInstance();
        queues = new Queues();
        rob = new ROB();
        vfu0 = new BlankFU(queues);
        vfu1 = new MemoryFU(queues);
        vfu2 = new IntegerFU(queues,rob);
        vfu3 = new MultiplierFU(queues);
    }

    /**
     * Clock cycle received
     */
    @Override
    public void nextCycle() {
        rob.retire();

        // execute vfus
        RegisterFile.getInstance().forwardingAvailable = true;
        vfu0.nextCycle();
        vfu1.nextCycle();
        vfu2.nextCycle();
        vfu3.nextCycle();


        // data not available
        if (rf.production[consume]==null) {
            return;
        }

        instruction = rf.production[consume];

        if (queues.canAdd(instruction) && !rob.full()) {
            queues.addInstruction(instruction);
            rob.addInstruction(instruction);
            rf.production[consume] = null;
        }



    }

    /**
     * Clears stage
     */
    @Override
    public void clear() {
        instruction = null;
        rob.reload();
        queues.reload();
        vfu1.reload();
        vfu2.reload();
        vfu3.reload();
    }

    /**
     * Displays stage
     */
    @Override
    public void display() {
        System.out.print("[Execution stage]: ");
        vfu1.display();
        vfu2.display();
        vfu3.display();
        System.out.println();
        rob.display();
        queues.display();
    }

    /**
     * Id of stage
     * @param id unique number of the stage
     */
    @Override
    public void setId(int id) {
        produce = id;
        consume = id -1;
    }


}
