package assembler.atom;

import assembler.Assemble;
import assembler.symbol.DefinedSymbol;
import java.util.HashMap;

public class Asterisk implements AtomicExpression {
	private int value;
	
	public Asterisk() {
		value = 0;
	}
	
    public int evaluate(Assemble assembler) {
	    return assembler.getCounter();
    }
    
    public String toString() {
    	return "*";
    }
}