package simulator.io;

import main.Sign;
import main.Word;
import main.Byte;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents a Tape Unit which is an input output system for MIX.
 * The Block Size is 100 words and for this implementation, the total words
 * capable of being stored is 4000 words.
 */
public class TapeUnit implements InputDevice, OutputDevice {
    private Word[] myMemory;
    private int tapePtr;
    private boolean accessed;
    private int number;

    public TapeUnit(int number) {
        myMemory = new Word[4000];
        tapePtr = 0;
        accessed = false;
        try {
            FileReader fr = new FileReader("tape" + number + ".mix");
            BufferedReader br = new BufferedReader(fr);
            for (int i = 0; i < 4000; i++) {
                String[] in = br.readLine().split(" ");
                myMemory[i] = new Word(in[0].equals("+"),
                        Integer.parseInt(in[1]),
                        Integer.parseInt(in[2]),
                        Integer.parseInt(in[3]),
                        Integer.parseInt(in[4]),
                        Integer.parseInt(in[5]));
            }
        }
        catch (IOException e) {
            for (int i = 0; i < 4000; i++) {
                myMemory[i] = new Word();
            }
        }
    }

    @Override
    public int getBlockSize() {
        return 100;
    }

    @Override
    public void printWord(Word w) {
        myMemory[tapePtr] = new Word(w);
        tapePtr++;
        accessed = true;
    }

    @Override
    public Word getWord() {
        Word w = new Word(myMemory[tapePtr]);
        tapePtr++;
        accessed = true;
        return w;
    }

    @Override
    public void control(int M) {
        tapePtr += M * getBlockSize();
        accessed = true;
    }

    public boolean isAccessed() {
        return accessed;
    }

    @Override
    public void close() {
        if (isAccessed()) {
            try {
                PrintWriter writer = new PrintWriter("tape" + number + ".mix", "UTF-8");
                for (int i = 0; i < 4000; i++) {
                    writer.write(myMemory[i].toString() + "\n");
                }
                writer.close();
            }
            catch(IOException e) {
            }
        }
    }
}
