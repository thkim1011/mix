package main;

import assembler.Assembler;
import debugger.Debugger;
import simulator.Simulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Handle number of arguments
        if (args.length == 1) {
            String temp = args[0];
            args = new String[2];
            args[0] = "-s";
            args[1] = temp;
        }
        else if (args.length != 2) {
            System.out.println("Must provide at least file name.");
            return;
        }

        if (args[0].equals("--assemble") || args[0].equals("-a")) {
            Assembler asm = new Assembler();
            PrintWriter writer = new PrintWriter("a.mix", "UTF-8");

            asm.assemble(getInput(args[1]));

            Word[] memory = asm.getMemory();
            for (int i = 0; i < 4000; i++) {
                writer.println(memory[i].toString());
            }

            writer.close();
        }
        else if (args[0].equals("--debug") || args[0].equals("-d")) {
            Debugger debug = new Debugger(getInput(args[1]));
            debug.start();
        }
        else if (args[0].equals("--simulate") || args[0].equals("-s")) {
            Assembler asm = new Assembler();
            asm.assemble(getInput(args[1]));
            Simulator sim = new Simulator(asm.getMemory(), asm.getCounter());
            sim.simulate();
            sim.closeDevices();
        }
    }

    /**
     * Gets the program with name fileName from io.
     * @param fileName is the name of the file.
     * @return the program without comments as an array of the program lines.
     * @throws IOException for io.
     */
    private static List<String> getInput(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader in = new BufferedReader(fr);
        String line;
        List<String> program = new ArrayList<>();
        while(true) {
            line = in.readLine();
            if (line == null) {
                break;
            }
            program.add(line);
        }
        in.close();
        fr.close();
        return program;
    }
}
