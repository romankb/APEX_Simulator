/*
 * Project: APEX_Simulator
 * Copyright (c) 2016, Roman Kurbanov,
 * Binghamton University.
 * All rights reserved.
 */

package apexsimulator.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class implements methods to read lines from the file.
 *
 * <p>Implements {@code FileInterface}
 *
 * @author Roman Kurbanov
 */
public class FileProcessor implements FileInterface {

    private BufferedReader reader;
    private String inputFile;

    /**
     * initializes member variables. Stream will be opened on demand only
     * @param inputFileIn file to read
     */
    public FileProcessor(String inputFileIn) {
        inputFile = inputFileIn;
    }


    /**
     * Reads one line at a time from the file
     *
     * @return read out string or null if nothing to read
     */
    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Cannot read file: \""+inputFile+"\"");
            System.out.println("Exiting");
            System.exit(2);
        }
        return null;
    }

    /**
     * Tries to open file stream
     */
    @Override
    public void openFile() {
        try{
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: \""+inputFile+"\"");
            System.out.println("Exiting");
            System.exit(ErrorCodes.FILE_ERROR);
        }

    }

    /**
     * Tries to close file streem
     */
    @Override
    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Cannot close file: \""+inputFile+"\"");
            System.out.println("Exiting");
            System.exit(ErrorCodes.FILE_ERROR);
        }
    }
}
