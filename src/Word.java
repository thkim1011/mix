/**
 * Word Class - This represents a sign and five bytes.
 * @author Tae Hyung Kim
 *
 */

public class Word {
	
    private Integer[] a;
    private int value;
    private boolean overflow;

    /**
     * Constructor for Word class
     * @param sign Sign of the word as -1 or 1
     * @param a1 First byte
     * @param a2 Second byte
     * @param a3 Third byte
     * @param a4 Fourth byte
     * @param a5 Fifth byte
     */
    public Word(int sign, int a1, int a2, int a3, int a4, int a5) {
        // Precondition Checking
        if(sign != 1 && sign != -1) {
            throw new IllegalArgumentException("sign must be either -1 or 1");
        }
        if(0 > a1 || 64 <= a1) {
            throw new IllegalArgumentException("a1 must be between 0 and 63");
        }
        if(0 > a2 || 64 <= a2) {
            throw new IllegalArgumentException("a2 must be between 0 and 63");
        }
        if(0 > a3 || 64 <= a3) {
            throw new IllegalArgumentException("a3 must be between 0 and 63");
        }
        if(0 > a4 || 64 <= a4) {
            throw new IllegalArgumentException("a4 must be between 0 and 63");
        }
        if(0 > a5 || 64 <= a5) {
            throw new IllegalArgumentException("a5 must be between 0 and 63");
        }

        a = new Integer[6];
        a[0] = sign;
        a[1] = a1;
        a[2] = a2;
        a[3] = a3;
        a[4] = a4;
        a[5] = a5;
        // TODO: Check to see if this works perfectly
        value = ((a[0] >= 0) ? 1 : -1) * (a[1] * 16777216 + a[2] * 262144 + a[3] * 4096 + a[4] * 64 + a[5]);
        
        overflow = false;
    }
    
    /**
     * Constructor for Word class. This was necessary due to the existence of both positive and negative zero in the MIX language.
     * @param sign Sign represented by either 1 or -1
     * @param x The value of the word as a 5 digit base 64 integer
     */
    public Word(int sign, int x) {
        // Precondition checking
        if(sign != -1 && sign != 1) {
            throw new IllegalArgumentException("sign must be either -1 or 1");
        }
        if(!(0 <= x && x < 1073741823)) {
            throw new IllegalArgumentException("x must be greater than -64^5 and less than 64^5");
        }
    	value = x;
    	a = new Integer[6];
        a[0] = (x >= 0) ? 1 : -1;
        x = Math.abs(x);
        for(int i = 5; i >= 1; i--) {
            a[i] = x % 64;
            x = x / 64;
        }
        overflow = false;
    }
    
    // TODO: check if this constructor works properly
    public Word(int sign, int x, FPart field) {
        // Precondition checking
        if(sign != -1 && sign != 1) {
            throw new IllegalArgumentException("sign must be either -1 or 1");
        }
        if(!(0 <= x && x < 1073741823)) {
            throw new IllegalArgumentException("x must be greater than -64^5 and less than 64^5");
        }
    	value = x;
    	a = new Integer[6];
        a[0] = (x >= 0) ? 1 : -1;
        x = Math.abs(x);
        for(int i = 5; i >= 1; i--) {
            a[i] = x % 64;
            x = x / 64;
        }

        // Check if field is valid
        int left = field.getLeft();
        int right = field.getRight();

        if(!(0 <= left && left <= 5) || !(0 <= right && right <= 5) || left > right) {
            throw new IllegalArgumentException("Field value is invalid");
        }

        // Set unspecified positions to null;
        for(int i = 0; i <= 5; i++) {
            if(!(left <= i && i <= right)) {
                a[i] = null;
            }
        }
        overflow = false;
    }

    /**
     * Default Constructor
     */
    public Word() {
    	a[0] = 1;
    	for(int i = 5; i > 0; i--) {
    		a[i] = 0;
    	}
    }
    
    /**
     * Implementation of toString() from Object
     * @return A String consisting of the sign and the five bytes separated by spaces.
     */
    public String toString() {
        return ((a[0] >= 0) ? "+" : "-") + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " + a[5];
    }

    /**
     * getValue()
     * @return The value of this word
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Overwrites this word with another and a field specification
     * @param other
     * @param field
     */
    public void overwrite(Word other) {
    	// Interpret field
    	for (int i = 0; i <= 6; i++) {
    		if(other.a[i] != null) {
    			this.a[i] = other.a[i];
    		}
    	}
    }
}