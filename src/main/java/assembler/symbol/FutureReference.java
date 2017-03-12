package assembler.symbol;

import assembler.APart;

import java.util.HashMap;


public class FutureReference implements Symbol, APart { 
    private String myName;
    private int myValue;
    private int myPosition;

    public FutureReference(String addr, int position, boolean isComputerGenerated) {
    	if(!isComputerGenerated && !Symbol.isValidSymbolName(addr)) {
    		throw new IllegalArgumentException("Your Future Reference is not a valid variable name.");
    	}
        myName = addr;
        myValue = 0;
        myPosition = position;
    }

    public int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
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