package simulator.io;

import main.Constants;
import main.Word;

import java.util.Scanner;

public class TypeWriter implements InputDevice {
    private static Scanner scan = new Scanner(System.in);

    private int counter;
    private String currentLine;

    @Override
    public int getBlockSize() {
        return 14;
    }

    @Override
    public Word getWord() {
        if (counter == 0) {
            currentLine = reformat(scan.nextLine());
        }
        Word w = new Word();
        for (int i = counter; i < counter + 5; i++) {
            w.setByte(i - counter + 1, Constants.CHARACTER_CODE.get(currentLine.charAt(i)));
        }
        counter = (counter + 5) % 70;

        return w;
    }

    private String reformat(String line) {
        if (line.length() >= 70) {
            return line.substring(0, 70);
        }
        for (int i = line.length(); i < 70; i++) {
            line += " ";
        }
        return line;
    }
}
