package apexsimulator.components.stages;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.components.registerfile.RegisterFile;

/**
 * @author Roman Kurbanov
 */
public class Execution implements StageInterface{

    Instruction instruction;
    RegisterFile rf;
    private int produce;
    private int consume;
    public Execution() {
        rf = RegisterFile.getInstance();
    }

    /**
     * Clock cycle received
     */
    @Override
    public void nextCycle() {
        // end of operations
        if (GlobalVars.pipeline_frozen) {
            return;
        }

        // data not available
        if (rf.production[consume]==null) {
            // do internal stuff, do not return
            return;
        }

        instruction = rf.production[consume];
        // do smth with instruction
        rf.production[consume] = null;
    }

    /**
     * Clears stage
     */
    @Override
    public void clear() {
        instruction = null;
        // clear ROB and queues
    }

    /**
     * Displays stage
     */
    @Override
    public void display() {
        if (instruction==null) {
            System.out.print("[E]:EMPTY; ");
        } else {
            System.out.printf("[E]:%s; ", instruction.getInstruction());
        }
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
