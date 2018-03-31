package simulator;

import main.Constants;
import main.Word;

public class IODevice {
    public int getBlockSize() {
        return 24;
    }

    public void printWord(Word w) {
        String repr = "";
        for (int i = 1; i <= 5; i++) {
            repr += Constants.CHARACTERS[w.getByte(i)];
        }
        System.out.print(repr);
    }
}
