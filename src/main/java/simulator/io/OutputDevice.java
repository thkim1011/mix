package simulator.io;

import main.Constants;
import main.Word;

public interface OutputDevice extends IODevice {
    default void printWord(Word w) {
        String repr = "";
        for (int i = 1; i <= 5; i++) {
            repr += Constants.CHARACTERS[w.getByte(i)];
        }
        System.out.print(repr);
    }
}
