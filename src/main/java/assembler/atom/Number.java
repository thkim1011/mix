package assembly.atom;

import assembly.symbol.DefinedSymbol;

import java.util.HashMap;

public class Number implements AtomicExpression {
    private int myNumber; 

    public Number(int number) {
        myNumber = number;
    }
    public int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
        return myNumber;
    }

    public String toString() {
    	return "" + myNumber;
    }
}