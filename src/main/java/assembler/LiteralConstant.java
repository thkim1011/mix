package assembler;

import main.Sign;
import main.Byte;

public class LiteralConstant {
    private String myName;
    private String myWVal;
    private Sign mySign;
    private Byte myByte1;
    private Byte myByte2;

    public LiteralConstant(String name, String wval, Sign sign, Byte byte1, Byte byte2) {
        myName = name;
        myWVal = wval;
        mySign = sign;
        myByte1 = byte1;
        myByte2 = byte2;
    }

    public String getConLine() {
        return myName + " CON " + myWVal;
    }

    public void updateBytes(int value) {
        mySign.setSign(value >= 0);
        myByte1.setValue(value >> 6);
        myByte2.setValue(value & 0x3F);
    }
}
