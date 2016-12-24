/**
 * Word Class - This represents a sign and five bytes.
 * @author Tae Hyung Kim
 *
 */

// TODO: separate getSign and getByte (currently all incorporated into getSign)
package main;
import assembly.*;

public class Word {
	
    private Integer[] a;
    private int value;
    private boolean overflow;

    
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
    public Word(String command, APart address, IPart index, FPart field) {
    	a = new Integer[6];
    	int addr = address.evaluate();
    	//a[0] = (addr >= 0) ? 1 : -1;
    	if(addr >= 0) {
    		a[0] = 1;
    	}
    	else {
    		a[0] = -1;
    	}
    	addr = Math.abs(addr);
    	a[1] = addr / 64;
    	a[2] = addr % 64;
    	a[3] = index.getValue();
    	a[4] = field.getValue();
    	a[5] = Assemble.convertToByte(command);
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
    public int getByte(int pos) {
    	// Precondition checking
    	if(!(0 <= pos && pos <= 5)) {
    		throw new IllegalArgumentException("The given position is invalid.");
    	}
    	return a[pos];
    }
    public void setByte(int pos, int value) {
    	// Precondition checking
    	if(!(0 <= pos && pos <= 5)) {
    		throw new IllegalArgumentException("The given position is invalid.");
    	}
    	a[pos] = value;
    }
    public void setAllBytes(Word other) {
    	// Interpret field
    	for (int i = 0; i <= 6; i++) {
    		if(other.a[i] != null) {
    			this.a[i] = other.a[i];
    		}
    	}
    }
    
    public void execute() {
    	switch(a[5]) {
    	case 5: HLT(); break;
    	case 8: LDA(); break;
    	case 9: LDi(1); break;
    	case 10: LDi(2); break;
    	case 11: LDi(3); break;
    	case 12: LDi(4); break;
    	case 13: LDi(5); break;
    	case 14: LDi(6); break;
    	case 15: LDX(); break;
    	case 24: STA(); break;
    	case 25: STi(1); break;
    	case 26: STi(2); break;
    	case 27: STi(3); break;
    	case 28: STi(4); break;
    	case 29: STi(5); break;
    	case 30: STi(6); break;
    	case 31: STX(); break;
    	
    	}
    }
    // Loading Operators
    
    private void LDA() {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int command = a[5];
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	int left = field / 8;
    	int right = field % 8;
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int i = 5, counter = right; i >= 0; i--, counter--) {
    		if(counter >= left) {
    			MIX.rA[i] = Assemble.assembled[memory].getByte(counter);
    		}
    		else {
    			MIX.rA[i] = 0;
    		}
    	}
    }
    
    private void LDi(int i) {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int command = a[5];
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	int left = field / 8;
    	int right = field % 8;
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int j = 2, counter = right; j >= 0; j--, counter--) {
    		if(counter >= left) {
    			MIX.rI[i][j] = Assemble.assembled[memory].getByte(counter);
    		}
    		else {
    			MIX.rI[i][j] = 0;
    		}
    	}
    }
    private void LDX() {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	int left = field / 8;
    	int right = field % 8;
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int i = 5, counter = right; i >= 0; i--, counter--) {
    		if(counter >= left) {
    			MIX.rX[i] = Assemble.assembled[memory].getByte(counter);
    		}
    		else {
    			MIX.rX[i] = 0;
    		}
    	}
    }
    
    private void LDAN() {
    	
    }
    
    // Storing Operators
    private void STA() {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int left = field/ 8;
    	int right = field % 8;
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int counter = right, i = 5; counter >= left; counter--, i-- ) {
    		Assemble.assembled[memory].setByte(counter, MIX.rA[i]);
    	}
    }
    
    private void STX() {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int left = field/ 8;
    	int right = field % 8;
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int counter = right, i = 5; counter >= left; counter--, i-- ) {
    		Assemble.assembled[memory].setByte(counter, MIX.rX[i]);
    	}
    }
    
    private void STi(int i) {
    	int address = a[0] * ( a[1] * 64 + a[2] );
    	int index = a[3];
    	int field = a[4];
    	int left = field/ 8;
    	int right = field % 8;
    	int memory = address + MIX.rI[index][0] * (MIX.rI[index][1] * 64 + MIX.rI[index][2]);
    	if (!(left <= right && 0 <= left && right <= 5)) {
    		throw new IllegalArgumentException("Field specification must be representible in the form (L:R) where 0 <= L <= R <= 5.");
    	}
    	for(int counter = right, j = 5; counter >= left; counter--, j-- ) {
    		Assemble.assembled[memory].setByte(counter, MIX.rI[i][j]); //TODO: Test ST1 to ST6. 
    	}
    }
    
    
    
    
    // Miscellaneous Operators
    private void HLT() {
    	MIX.isHalted = true;
    }
}