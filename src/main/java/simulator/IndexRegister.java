package simulator;

/**
 * The index register is a smaller variant of the register, but we will "allow" modification of
 * bytes 1, 2, 3, in addition to 4, 5 (as in they can try but it won't do anything).
 */
public class IndexRegister extends Register {
    /**
     * Sets the byte at pos to val. This only works if the pos is 4 or 5.
     * Otherwise, we do nothing.
     * @param pos is the position.
     * @param val is the value.
     */
    @Override
    public void setByte(int pos, int val) {
        if (pos > 3) {
            super.setByte(pos, val);
        }
    }
}
