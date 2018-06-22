package simulator.io;

import main.Constants;
import main.Word;
import simulator.io.IODevice;

public class LinePrinter implements OutputDevice {
    @Override
    public int getBlockSize() {
        return 24;
    }

    @Override
    public void control(int M) {
        if (M != 0) {
            throw new IllegalArgumentException("M should be zero.");
        }
    }
}
