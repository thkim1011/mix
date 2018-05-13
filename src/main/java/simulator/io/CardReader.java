package simulator.io;

import main.Word;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CardReader implements InputDevice {
    private Reader read;

    public CardReader() {
        read = new InputStreamReader(System.in);
    }

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public Word getWord() {
        Word w = new Word();
        return w;
    }
}
