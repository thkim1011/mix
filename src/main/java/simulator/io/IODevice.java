package simulator.io;

public interface IODevice {
    int getBlockSize();
    default void control(int M) {
        return;
    }
    default void close() {
        return;
    }

    static String reformat(String line, int blockSize) {
        if (line.length() >= blockSize * 5) {
            return line.substring(0, blockSize * 5);
        }
        for (int i = line.length(); i < blockSize * 5; i++) {
            line += " ";
        }
        return line;
    }
}