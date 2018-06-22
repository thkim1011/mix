package simulator.io;

import main.Word;

public class DiskUnit implements InputDevice, OutputDevice {
    public DiskUnit(int i) {

    }
    @Override
    public int getBlockSize() {
        return 100;
    }

    @Override
    public void printWord(Word w) {

    }

    @Override
    public Word getWord() {
        return null;
    }
}
