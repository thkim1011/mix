package assembler;

import assembler.symbol.DefinedSymbol;

import java.util.HashMap;

public class IPart {
	private Expression myIndex;
	
    public IPart() {
        myIndex = new Expression("0");
    }

    public IPart(String index) {
    	if(index.length() == 0) {
    		myIndex = new Expression("0");
    	}
    	else {
    		if(index.charAt(0) != ',') {
    			throw new IllegalArgumentException("Indices must begin with a comma");
    		}
    		else {
    			myIndex = new Expression(index.substring(1));
    		}
    	}
    }
    
    public int getValue(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
    	return myIndex.evaluate(counter, definedSymbols);
    }
}