package apexsimulator.components;

import apexsimulator.components.instructions.Instruction;
import apexsimulator.components.registerfile.GlobalVars;

/**
 * @author Roman Kurbanov
 */
public class IssueQueue {
    public Instruction[] issueQueue;
    public int size;

    private volatile static IssueQueue iq;

    private IssueQueue() {
        issueQueue = new Instruction[GlobalVars.IQ_SIZE];
        size = 0;
    }

    public void reload() {

    }
}
