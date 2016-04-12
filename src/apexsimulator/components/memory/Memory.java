/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.components.memory;

import apexsimulator.components.DisplayInterface;
import apexsimulator.components.LoaderInterface;
import apexsimulator.components.registerfile.GlobalVars;
import apexsimulator.util.ErrorCodes;
import apexsimulator.util.FileInterface;
import apexsimulator.util.FileProcessor;

import java.util.ArrayList;

/**
 * This class implements i-cache and d-cache
 * Taking into account its uniqueness across the program
 * this class is implemented as Singleton
 *
 * @author Roman Kurbanov
 */
public class Memory implements LoaderInterface, MemoryInterface, DisplayInterface{
    private Integer[] dCache;
    private String[] iCache;

    private int dCacheSize = 10000;
    public int iCacheSize;
    private int instrStart = GlobalVars.INSTR_START;

    private String fileName;
    private FileInterface fileReader;

    //unique instance of the class
    private volatile static Memory memory;

    /*
     Private constructor, workaround for singleton
     */
    private Memory() {
    }

    /**
     * creates or returns previously created unique instance
     * of the class
     * this method is thread safe
     *
     * @return unique instance of {@code Memory} class
     */
    public static Memory getInstance(){
        if (memory == null) {
            synchronized(Memory.class) {
                if (memory == null) {
                    memory = new Memory();
                }
            }
        }
        return memory;
    }


    /**
     * Attempts to load instructions into memory
     */
    @Override
    public void loadProgram() {

        fileReader = new FileProcessor(fileName);
        dCache = new Integer[dCacheSize];

        // resetting data segment as well
        for(int i = 0; i < dCacheSize; i++) {
            //dCache[i] = 0;
            dCache[i] = null;
        }
        fileReader.openFile();
        ArrayList<String> temp = new ArrayList<>();
        String tempInstr;

        // reads file till the end
        while(true) {
            tempInstr = fileReader.readLine();
            if (tempInstr==null) break;
            temp.add(tempInstr);
        }

        iCache = new String[temp.size()];
        iCacheSize = temp.size();
        iCache = temp.toArray(iCache);
        fileReader.closeFile();
    }


    /**
     * Returns an instruction at particular address
     * halts the execution if wrong address is passed
     *
     * @param address addreess of instruction
     * @return Instruction at a particular address
     */
    @Override
    public String fetch(int address) {
        // last instruction
        //if ((iCacheSize-1) == address) {
        //    GlobalVars.pipeline_frozen = true;
        //}
        int maxAddr = instrStart + iCacheSize-1;
        if (address<instrStart || address>maxAddr) {
            System.out.println("Error! Program is trying to access wrong memory segment.");
            System.out.println("Exiting...");
            //System.out.println(address);
            System.exit(ErrorCodes.SEGFAULT_ERROR);
        }

        return iCache[address-instrStart];
    }

    /**
     * Returns data read from memory
     *
     * @param address address of memory area
     * @return integer value stored in memory
     */
    @Override
    public int readData(int address) {
        if (address<0 || address>(dCacheSize-1)) {
            System.out.println("Error! Program is trying to access wrong memory segment.");
            System.out.println("Exiting...");
            System.exit(ErrorCodes.SEGFAULT_ERROR);
        }

        if (dCache[address] == null) {
            System.out.println("Error! Program is trying to access uninitialized memory area");
            System.out.println("Exiting...");
            System.exit(ErrorCodes.SEGFAULT_ERROR);
        }

        return dCache[address];
    }

    /**
     * Writes data to data cache
     * @param address address of memory cell
     * @param val value to be written
     */
    public void writeMem(int address, int val) {
        if (address<0 || address>(dCacheSize-1)) {
            System.out.println("Error! Program is trying to access wrong memory segment.");
            System.out.println("Exiting...");
            System.exit(ErrorCodes.SEGFAULT_ERROR);
        }

        dCache[address] = val;
    }

    /**
     * Purges existing data in d-cache and reloads instructions
     * in i-cache
     */
    @Override
    public void reload() {
        // basically a wrapper
        loadProgram();
    }

    /**
     * Sets filename to read instructions from
     *
     * @param fileName filename with instructions
     */
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Display contents of first 100 memory locations
     */
    @Override
    public void display() {
        int count = 0;
        System.out.println("---Contents of accessed memory:---");
        for (int i = 0; i < dCacheSize ; i++) {
            if (dCache[i]!= null) {
                System.out.printf(" Mem[%d]: %d;", i, dCache[i]);
                count++;
            }
            if (count==100) {
                break;
            }
        }
        System.out.println();
        System.out.println("----------------------------------");
    }
}
