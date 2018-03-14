package assembler.symbol;


/**
 * <b>FutureReference</b>. This class defines a future reference within the assembly program.
 * The class first creates a "one-time set" sign and two bytes which will be shared with the assembled
 * instruction Word, so that when these sign and bytes are set in the future, the assembled instruction
 * will share the future values. Refer to see a "demo".
 */
public class FutureReference {
    private String myName;
    private FutureSign mySign;
    private FutureByte myByte1;
    private FutureByte myByte2;

    public FutureReference(String name) {
        myName = name;
        mySign = new FutureSign();
        myByte1 = new FutureByte();
        myByte2 = new FutureByte();
    }

    public FutureByte getFirstByte() {
        return myByte1;
    }

    public FutureByte getSecondByte() {
        return myByte2;
    }

    public void updateBytes(boolean sign, int value) {
        myByte1.setValue(value >> 6);
        myByte2.setValue(value & 0x3F);
    }

    public String getName() {
        return myName;
    }
}