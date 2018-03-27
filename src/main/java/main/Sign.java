package main;

public class Sign {
    boolean mySign;

    public Sign(boolean sign) {
        mySign = sign;
    }

    public boolean getSign() {
        return mySign;
    }

    public void setSign(boolean sign) {
        mySign = sign;
    }

    @Override
    public String toString() {
        return mySign ? "+" : "-";
    }
}
