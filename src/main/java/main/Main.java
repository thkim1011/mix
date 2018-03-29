package main;

import assembler.Assembler;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Must provide two arguments: option and filename");
            return;
        }

        if (args[0].equals("--assemble") || args[0].equals("-a")) {
            Assembler asm = new Assembler();
            FileReader fr = new FileReader(args[1]);
            BufferedReader in = new BufferedReader(fr);
            PrintWriter writer = new PrintWriter("a.mix", "UTF-8");

            String line;
            // MEMORY
            while(true) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                if (!line.equals("") && line.charAt(0) != '*') {
                    asm.assemble(line);
                }
            }

            Word[] memory = asm.getMemory();
            for (int i = 0; i < 4000; i++) {
                writer.println(memory[i].toString());
            }

            fr.close();
            in.close();
            writer.close();
        }

        else if (args[0].equals("--simulate") || args[0].equals("-s")) {

        }
    }
}
