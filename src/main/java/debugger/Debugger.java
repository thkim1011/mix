package debugger;

import assembler.Assembler;
import simulator.Simulator;
import java.util.List;
import java.util.ArrayList;


public class Debugger {
    private Assembler myAssembler;
    private Simulator mySimulator;
    private List<Integer> myBreakPoints;

    public Debugger(List<String> program) {
        myAssembler = new Assembler();
        mySimulator = new Simulator();
        myBreakPoints = new ArrayList<>();

        myAssembler.assemble(program);

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
                break;
            case "p":
            case "print":
                break;
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

}

