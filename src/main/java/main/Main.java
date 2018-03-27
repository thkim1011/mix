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

            String line = "";
            // MEMORY
            Word[] memory = new Word[4000];
            for (int i = 0 ; i < 4000; i++) {
                memory[i] = new Word();
            }

            while(line != null) {
                line = in.readLine();
                int counter = asm.getCounter();
                Word assembled = asm.assemble(line);
                memory[counter] = assembled;
            }

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
