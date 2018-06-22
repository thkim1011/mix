package simulator.io;

import main.Word;

public class PaperTape implements InputDevice, OutputDevice {
    public int getBlockSize() {
        return 14;
    }

    public Word getWord() {
        return null;
    }
}
