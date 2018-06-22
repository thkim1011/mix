package simulator.io;

import main.Constants;
import main.Word;

public interface OutputDevice extends IODevice {
    void printWord(Word w);
}
