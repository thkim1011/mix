package assembler.atom;

import assembler.Assemble;
import assembler.symbol.DefinedSymbol;

import java.util.HashMap;

public class Number implements AtomicExpression {
    private int myNumber; 

    public Number(int number) {
        myNumber = number;
    }
    public int evaluate(Assemble assembler) {
        return myNumber;
    }

    public String toString() {
    	return "" + myNumber;
    }
}