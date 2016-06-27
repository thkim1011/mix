public class Byte { 
    private int myValue;
    private int overflow;
    
    public static int bytesize = 64;
    
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

    public String toString() {
        return "" + myValue;

        // Implement a way to do binary if sepcificed by commandline arguments
    }

    public int getOverflow() {
        return overflow;
    }

    public void setValue(int newValue) {
        myValue = newValue;
    }
}