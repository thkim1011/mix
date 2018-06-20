package debugger;

import assembler.Assembler;
import simulator.Simulator;
import java.util.List;
import java.util.ArrayList;


public class Debugger {
    private Assembler myAssembler;
    private Simulator mySimulator;
    private List<Integer> myBreakPoints;

    public Debugger(Assembler asm, Simulator sim) {
        myAssembler = asm;
        mySimulator = sim;
        myBreakPoints = new ArrayList<>();
    }

    /**
     * Interprets line as a debugger command.
     * @param line
     */
    public void interpret(String line) {
        
    }

    public void stepProgram() {

    }

    public void addBreak() {

    }

    public void runProgram() {

    }

    public void continueProgram() {

    }

}

