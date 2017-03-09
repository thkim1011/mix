package assembly.symbol;

import assembly.Assemble;
import assembly.APart;


public class FutureReference implements Symbol, APart { 
    private String myName;
    private int myValue;
    private int myPosition;

    public FutureReference(String addr, int position) {
    	if(!Symbol.isValidSymbolName(addr)) {
    		throw new IllegalArgumentException("Your Future Reference is not a valid variable name.");
    	}
        myName = addr;
        myValue = 0;
        myPosition = position;
    }

    public int evaluate() {
        return myValue;
    }
    
    public String getName() {
    	return myName;
    }

    public int getPosition() { return myPosition; }

    public String toString() {
    	return myName;
    }
}