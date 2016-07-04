public class Word {
    private int sign;
    private Byte[] a;
    public Word(int sign,  Byte a1, Byte a2, Byte a3, Byte a4,Byte a5) {
        a = new Byte[6];
        a[0] = new Byte(sign);
        a[1] = a1;
        a[2] = a2;
        a[3] = a3;
        a[4] = a4;
        a[5] = a5;
    }

    public String toString() {
        return ((a[0].getValue() > 0) ? "+" : "-") + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " + a[5];
    }

}