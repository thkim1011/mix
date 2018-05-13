package simulator.io;

import simulator.io.IODevice;

public class LinePrinter implements OutputDevice {
    public int getBlockSize() {
        return 24;
    }
}
