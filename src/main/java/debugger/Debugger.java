package debugger;

import assembler.Assembler;
import main.Word;
import simulator.Simulator;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Debugger class.</b> The debugger provides the debugging tools
 * for MIX. The debugger makes use of the assembler and the
 * simulator. However, rather than simply assembling the whole
 * program, the assembler is used to also construct a mapping from
 * main memory to the lines of the program.
 *
 * The debugger uses commands which are similar to gdb's.
 *
 * The following instance variables are important to understand.
 * First, the myProgram array is of length 4000 and corresponds
 * to main memory. The isModified array is also of length 4000
 * and indicates whether a certain memory location has been
 * modified from the original program (indeed MIX does allow one
 * to write self-modifying programs) in the case that the debugger
 * views a certain line. myLineNumbers store the line numbers of the
 * program. 0 indicates that memory location does not correspond to
 * a line number of the program.
 */
public class Debugger {
    private Assembler myAssembler;
    private Simulator mySimulator;
    private List<Integer> myBreakPoints;
    private String[] myProgram;
    private int[] myLineNumbers;
    private boolean[] isModifed;

    /**
     * Constructs the debugger.
     * @param program is the list of strings which
     *                represent the program.
     */
    public Debugger(List<String> program) {
        myAssembler = new Assembler();
        myBreakPoints = new ArrayList<>();
        myProgram = new String[4000];
        myLineNumbers = new int[4000];

        // Construct mappings from memory to program
        for (int i = 1; i <= program.size(); i++) {
            String line = program.get(i - 1);
            if (line.equals("") || line.charAt(0) == '*') {
                continue;
            }
            myProgram[myAssembler.getCounter()] = program.get(i - 1);
            myLineNumbers[myAssembler.getCounter()] = i;
            myAssembler.assemble(program.get(i - 1));
        }

        mySimulator = new Simulator(myAssembler.getMemory(), myAssembler.getCounter());
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            System.out.println(myLineNumbers[mySimulator.getCounter()]
                    + "\t" + myProgram[mySimulator.getCounter()]);
            System.out.print("(mdb) ");
            line = scanner.nextLine();
            if (line.equals("quit") || line.equals("q")) {
                break;
            }
            interpret(line);
        }
    }

    /**
     * Interprets line as a debugger command.
     * @param line is the string entered as a command
     */
    public void interpret(String line) {
        String[] tokenized = line.split(" ");
        if (tokenized.length == 0) {
            return;
        }
        switch (tokenized[0]) {
            case "b":
            case "break":
                break;
            case "s":
            case "step":
            case "n":
            case "next":
                mySimulator.simulateNextWord();
                break;
            case "p":
            case "print":
                printValue(tokenized[1]);
                break;
            case "p/c":
            case "print/c":
                printChar(tokenized[1]);
                break;
            case "p/w":
            case "print/w":
                printWord(tokenized[1]);
            default:
                break;
        }
    }

    public void stepProgram() {

    }

    /**
     * @param memLoc is the location in memory of the breakpoint.
     */
    public void addBreak(int memLoc) {
        if (0 > memLoc || memLoc >= 4000) {
            throw new IllegalArgumentException("Specified memory location doesn't exist.");
        }
        myBreakPoints.add(memLoc);
    }

    public void clearBreak() {

    }

    public void runProgram() {

    }

    public void continueProgram() {

    }

    /**
     * The printChar method prints the value associated with
     * what is requested. It prints only three types of values:
     * MIX Expressions, Registers, and Memory locations. Expressions are evaluated
     * by the assembler while registers are called from the simulator.
     * It uses the helper method getWord which gets the associated word. printChar
     * then formats the word.
     * @param name
     */
    public void printChar(String name) {

    }

    public void printValue(String name) {
        System.out.println(findWord(name).getValue());
    }

    public void printWord(String name) {
        System.out.println(findWord(name));
    }

    private Word findWord(String name) {
        // Print Registers
        if (name.equals("rA")) {
            return mySimulator.getRegisterA().getWord();
        } else if (name.equals("rX")) {
            return mySimulator.getRegisterX().getWord();
        } else if (name.equals("rI1")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI2")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI3")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI4")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI5")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI6")) {
            return mySimulator.getIndexRegister(1).getWord();
        } else if (name.equals("rI7")) {
            return mySimulator.getJumpRegister().getWord();
        } else if (myAssembler.isWValue(name)) {
            return myAssembler.evaluateWValue(name);
        }
        // Print Memory
        else {
            String[] tokens = name.split("\\[|\\]");
            if (tokens.length == 2 &&
                    (tokens[0].equals("CONTENTS") || tokens[0].equals("contents"))) {
                return mySimulator.getMemory(findWord(tokens[1]).getValue());
            }
            else {
                throw new IllegalArgumentException("Given request not register, WValue, or Memory Request");
            }
        }
    }

}

