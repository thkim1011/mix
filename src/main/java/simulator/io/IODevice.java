package simulator.io;

public interface IODevice {
    int getBlockSize();
    default void control(int M) {
        return;
    }
    default void close() {
        return;
    }
}