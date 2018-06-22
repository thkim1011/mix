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
            currentLine = IODevice.reformat(scan.nextLine(), getBlockSize());
        }
        Word w = new Word();
        for (int i = counter; i < counter + 5; i++) {
            w.setByte(i - counter + 1, Constants.CHARACTER_CODE.get(currentLine.charAt(i)));
        }
        counter = (counter + 5) % 70;

        return w;
    }


}
