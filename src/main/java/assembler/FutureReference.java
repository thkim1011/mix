package assembler;

import main.Byte;
import main.Sign;

/**
 * <b>FutureReference</b>. This class defines a future reference within the assembly program.
 * The class first creates a "one-time set" sign and two bytes which will be shared with the assembled
 * instruction Word, so that when these sign and bytes are set in the future, the assembled instruction
 * will share the future values. Refer to see a "demo".
 */
public class FutureReference {
    private String myName;
    private Sign mySign;
    private Byte myByte1;
    private Byte myByte2;

    public FutureReference(String name, Sign sign, Byte byte1, Byte byte2) {
        myName = name;
        mySign = sign;
        myByte1 = byte1;
        myByte2 = byte2;
    }

    public String getName() {
        return myName;
    }

    public void updateBytes(int value) {
        mySign.setSign(value >= 0);
        myByte1.setValue(value >> 6);
        myByte2.setValue(value & 0x3F);
    }

    public String getConLine() {
        return myName + " CON 0";
    }
}