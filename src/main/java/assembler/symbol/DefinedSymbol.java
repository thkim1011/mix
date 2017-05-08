package assembler.symbol;

import assembler.Assemble;
import assembler.atom.AtomicExpression;

import java.util.HashMap;

public class DefinedSymbol implements Symbol, AtomicExpression, Comparable<DefinedSymbol> {
    private String myName;
    private int myValue;

    public DefinedSymbol(String name, int value) {
    	myName = name;
    	myValue = value;
    }

    public String getName() {
        return myName;
    }

    public int evaluate(Assemble assembler) {
        if(myValue != -1) {
            return myValue;
        }
        else {
            return assembler.getDefinedSymbols(myName);
        }
    }


    public String toString() {
    	return myName;
    }

    public int compareTo(DefinedSymbol other) {
        return myName.compareTo(other.myName);
    }

    // Note that this should only be called in the case that EQU is found.
    public void setValue(int value) {
        myValue = value;
    }

    public int getValue() {
        return myValue;
    }
}