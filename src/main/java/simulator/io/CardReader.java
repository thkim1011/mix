package simulator.io;

import main.Constants;
import main.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardReader implements InputDevice {
    private BufferedReader br;
    private int counter;
    private String currentLine;

    public CardReader() {
        try {
            FileReader fr = new FileReader("card-in.mix");
            br = new BufferedReader(fr);
        }
        catch (IOException e) {

        }
    }

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public Word getWord() {
        if (br == null) {
            throw new IllegalArgumentException("No Card Reader Found.");
        }
        // TODO: HANDLE EXCEPTIONS
        if (counter == 0) {
            try {
                currentLine = IODevice.reformat(br.readLine(), getBlockSize());
            }
            catch (IOException e) {

            }
        }
        Word w = new Word();
        for (int i = counter; i < counter + 5; i++) {
            w.setByte(i - counter + 1, Constants.CHARACTER_CODE.get(currentLine.charAt(i)));
        }
        counter = (counter + 5) % 80;
        return w;
    }
}
