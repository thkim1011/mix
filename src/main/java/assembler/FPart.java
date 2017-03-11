package assembler;

import assembler.symbol.DefinedSymbol;

import java.util.HashMap;

public class FPart {

    private Expression myField;

    public FPart(String field) {
        // Precondition checking
        if(field.charAt(0) != '(' || field.charAt(field.length() - 1) != ')') {
            throw new IllegalArgumentException("The field must begin with '(' and end with ')'");
        }
    	myField = (new Expression(field.substring(1, field.length() - 1)));
    }

    // TODO: create a new constructor for Expression that takes int
    public FPart(int value) {
        myField = new Expression(Integer.toString(value));
    }

    public int getLeft(int counter, HashMap<String, DefinedSymbol> definedSymbols) {

        return myField.evaluate(counter, definedSymbols) / 8;
    }
    public int getRight(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
        return myField.evaluate(counter, definedSymbols) % 8;
    }
    public int getValue(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
    	return myField.evaluate(counter, definedSymbols);
    }
}