package assembler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import assembler.symbol.LocalDefinedSymbol;
import main.Constants;
import main.Word;
import main.Byte;
import assembler.symbol.DefinedSymbol;
import assembler.symbol.FutureReference;
import assembler.symbol.Symbol;

/**
 * <b>Assemble class.</b> This implementation of MIX consists of three
 * components: the assembler, the debugger, and the simulator. This class acts
 * to perform the operations of the assembler. In order to use this class, an
 * instance of an assembler must be created first using the constructor, which
 * contains a parameter for the file, and then the assemble method must be run.
 *
 * @author Tae Hyung Kim
 */

public class Assemble {
    // Counter and Byte Size
    private int myCounter;
    private int myByteSize;
    // Symbols
    private HashMap<String, DefinedSymbol> myDefinedSymbols;
    private ArrayList<FutureReference> myFutureReferences;
    private ArrayList<FutureReference> myLocalFutureReferences;
    private ArrayList<LocalDefinedSymbol> myLocalDefinedSymbols;
    // Instructions
    private ArrayList<String> myProgram;
    private ArrayList<String[]> myFormattedProgram;
    private Word[] myAssembled;

    /**
     * The Assemble constructor creates an instance of the Assemble class which
     * contains the methods to assemble a myProgram written in MIXAL to MIX
     * machine code.
     *
     * @param filenameIn Name of the file which contains the MIX assembler myProgram.
     * @throws IOException
     */
    public Assemble(String filenameIn, String filenameOut) throws IOException {
        Constants.init();
        myCounter = 0;
        myByteSize = 64;
        myDefinedSymbols = new HashMap<String, DefinedSymbol>();
        myFutureReferences = new ArrayList<FutureReference>();
        myLocalDefinedSymbols = new ArrayList<LocalDefinedSymbol>();
        myLocalFutureReferences = new ArrayList<FutureReference>();

        myProgram = new ArrayList<String>();
        myFormattedProgram = new ArrayList<String[]>();
        myAssembled = new Word[4000];

        // Start Counter for Literal Constants
        Integer constants = 0;

        // Instantiate Local Symbols
        for (int i = 0; i <= 9; i++) {
            myDefinedSymbols.put(i + "B", new DefinedSymbol(i + "B", -1));
        }
        // Allot space for myAssembled
        for(int i = 0; i < 4000; i++) {
            myAssembled[i] = new Word();
        }

        // Deal with input output stuff
        BufferedReader fileIn = new BufferedReader(new FileReader(filenameIn));
        PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(filenameOut)));

        // Write the Program to the variable myProgram
        // Also add to the myFormattedProgram
        while (true) {
            String line = fileIn.readLine();

            if (line == null) {
                break;
            }

            line = line.toUpperCase();

            if (!line.equals("") && line.charAt(0) == '*') {
                // TODO: Deal with these comments
                continue;
            }

            myProgram.add(line);
            myFormattedProgram.add(partitionLine(line));
        }


        for (int i = 0; i < myFormattedProgram.size(); i++) {
            String symbol = myFormattedProgram.get(i)[0];
            String command = myFormattedProgram.get(i)[1];
            String address = myFormattedProgram.get(i)[2];
            // Check to see if there is a variable

            if (!symbol.equals("")) {
                // If Local Symbol

                if (symbol.matches("[0-9]H")) {
                    myLocalDefinedSymbols.add(new LocalDefinedSymbol(symbol.substring(0,1) + "F", myCounter));
                    myDefinedSymbols.put(symbol.substring(0,1) + "B", new DefinedSymbol(symbol.substring(0,1) + "B", myCounter));
                }
                // Note that symbol names cannot be 2B or 2F since this causes danger
                if (symbol.matches("[0-9](B|F)")) {
                    throw new IllegalArgumentException("You may not use local variables dH or dF as a symbol name.");
                }

                // Otherwise Defined Symbol
                else {
                    myDefinedSymbols.put(symbol, new DefinedSymbol(symbol, myCounter));
                }
            }

            // Process MIX commands
            if (command.equals("ORIG")) {
                WValue addr = new WValue(address);
                myCounter = addr.evaluate(myCounter, myDefinedSymbols).getValue();
                continue;
            }
            if (command.equals("EQU")) {
                WValue addr = new WValue(address);
                myDefinedSymbols.get(symbol).setValue(addr.evaluate(myCounter, myDefinedSymbols).getValue());
                continue;
            }
            if (command.equals("CON")) {
                WValue addr = new WValue(address);
                myAssembled[myCounter] = addr.evaluate(myCounter, myDefinedSymbols);
                myCounter++;
                continue;
            }
            if (command.equals("ALF")) {
                WValue addr = new WValue(address);
                //TODO: Finish ALF
                myCounter++;
                continue;
            }
            if (command.equals("END")) {
                WValue addr = new WValue(address);
                //TODO: FINISH END
                continue;
            }

            // Otherwise... Add command into the assembled program

            myAssembled[myCounter].setByte(5, new Byte(Constants.commands.get(command).getCode()));

            // Interpret the address section

            // TODO: Reformat code below...
            String in = myProgram.get(i);
            String[] linePartition = myFormattedProgram.get(i);
            String[] addrPartition = partitionAddress(address, command);

            // Get Value of IPART
            IPart iPart = new IPart(addrPartition[1]);
            // Get Value of FPart
            FPart fPart;
            if (addrPartition[2].equals("")) {
                fPart = new FPart(Constants.commands.get(linePartition[1]).getField());
            } else {
                fPart = new FPart(addrPartition[2]);
            }

            // Interpret APart -- I should do bug testing for this code
            APart aPart;
            String addr = addrPartition[0];
            if (addr.matches("[A-Z|0-9]+") && !addr.matches("[0-9]+")) { // If it is a normal symbol name
                // Check the validity of variable name
                if (!Symbol.isValidSymbolName(addr)) {
                    throw new IllegalArgumentException("Symbol name is invalid.");
                }
                // If the variable is not defined

                boolean isDefined = myDefinedSymbols.get(addr) != null;

                if (!isDefined) {
                    if(addr.matches("[0-9]F")) {
                        aPart = new FutureReference(addr, myCounter, false);
                        myLocalFutureReferences.add((FutureReference) aPart);
                    }
                    else {
                        aPart = new FutureReference(addr, myCounter, false);
                        myFutureReferences.add((FutureReference) aPart);
                    }
                }
                else {
                    aPart = new Expression(addr); // TODO: bad code... need to fix
                }

            }
            else {
                // IF Literal Constant...
                if (addr.matches("=.+=")) {
                    // TODO: add checking that END exists and is last line
                    String[] partition = new String[3];
                    partition[0] = "_" + constants;
                    partition[1] = "CON";
                    partition[2] = addr.substring(1, addr.length() - 1);
                    myFormattedProgram.add(myFormattedProgram.size() - 1, partition);
                    constants ++;
                    addr = partition[0];
                    aPart = new FutureReference(addr, myCounter, true);
                    myFutureReferences.add((FutureReference) aPart);
                }
                else {
                    if(addr.length() == 0) {
                        addr = "0";
                    }
                    aPart = new Expression(addr);
                }

                // TODO: it seems to be that operator with empty string do not function well.
            }

            // Create Instruction and add to memory
            myAssembled[myCounter] = new Word(linePartition[1], aPart, iPart, fPart, myCounter, myDefinedSymbols);

            myCounter++;
        }

        // Deal with all the Future references
        for(int i =0 ; i < myFutureReferences.size(); i++) {
            int position = myFutureReferences.get(i).getPosition();
            String name = myFutureReferences.get(i).getName();
            int value = myDefinedSymbols.get(name).evaluate(myCounter, myDefinedSymbols);
            myAssembled[position].setSign(true);
            myAssembled[position].setByte(1, new Byte(value/64));
            myAssembled[position].setByte(2, new Byte(value%64));
        }

        // Deal with all the Local Future References
        for(int i = 0; i < myLocalFutureReferences.size(); i++) {
            int position = myLocalFutureReferences.get(i).getPosition();
            String name = myFutureReferences.get(i).getName();
            //TODO: The following code is quite inefficient.
            //TODO: Create a better design to this program
            for(int j = 0; j < myLocalDefinedSymbols.size(); j++) {
                LocalDefinedSymbol d = myLocalDefinedSymbols.get(j);
                if(!(d.getValue() < position) && d.getName() == name) {
                    myAssembled[position].setSign(true);
                    myAssembled[position].setByte(1, new Byte(d.getValue()/64));
                    myAssembled[position].setByte(2,new Byte(d.getValue()%64));
                    break;
                }
            }
        }

        for(int i = 0; i < 4000; i++) {
            fileOut.println(myAssembled[i].toString());
        }
        fileIn.close();
        fileOut.close();
    }

    /**
     * Returns an array consisting of three strings which are the three parts of
     * a line of MIXAL code.
     *
     * @param in the line to be partitioned
     * @return an array of three strings
     */
    private static String[] partitionLine(String in) {
        StringBuilder line = new StringBuilder(in);
        String[] partition = new String[3];
        partition[0] = getFirstWord(line);
        partition[1] = getFirstWord(line);
        partition[2] = getFirstWord(line);
        return partition;
    }

    /**
     * Returns an
     *
     * @param address String referring to the address portion of MIX instruction
     * @param command String referring to the command portion of MIX instruction
     * @return A String[3] which contains the address split into its APart,
     * IPart, and FPart
     */
    private static String[] partitionAddress(String address, String command) {
        String[] partition = new String[3];

        // Extract FPart
        int field = address.indexOf("(");
        if (field != -1) {
            partition[2] = address.substring(field);
            address = address.substring(0, field);
        } else {
            partition[2] = "";
        }

        // Extract IPart
        int index = address.indexOf(",");
        if (index != -1) {
            partition[1] = address.substring(index);
            address = address.substring(0, index);
        } else {
            partition[1] = "";
        }
        // Extract APart
        partition[0] = address;
        return partition;
    }

    /**
     * getFirstWord method
     *
     * @param line A mutable string
     * @return Returns the first string before encountering a space
     */
    private static String getFirstWord(StringBuilder line) {
        int i = 0;
        int length = line.length();
        while (i < length && line.charAt(i) != ' ') {
            i++;
        }
        String first = line.substring(0, i);
        int j = i;
        while (j < length && line.charAt(j) == ' ') {
            j++;
        }
        line.delete(0, j);
        return first;
    }
}