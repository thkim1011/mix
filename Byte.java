public class Byte { 
    private int myValue;
    private int overflow;

    public Byte(int i) {
        myValue = i/64;
        overflow = i%64;
    }

    public Byte add(Byte b) {
        int sum = b.myValue + this.myValue;
        return new Byte(sum);
    }

    public int getValue() {
        return myValue;
    }

    public int getOverflow() {
        return overflow;
    }

    public void setValue(int newValue) {
        myValue = newValue;
    }
}