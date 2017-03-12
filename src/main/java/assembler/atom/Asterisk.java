package assembler.atom;

import assembler.symbol.DefinedSymbol;
import java.util.HashMap;

public class Asterisk implements AtomicExpression {
	private int value;
	
	public Asterisk() {
		value = 0;
	}
	
    public int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
	    return counter;
    }
    
    public String toString() {
    	return "*";
    }
}