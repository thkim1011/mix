public class DefinedSymbol implements Symbol, AtomicExpression {
    private String myName;
    private int myValue;

    public DefinedSymbol(String name) {
        myName = name;
        myValue = Assemble.counter;
    }
    public DefinedSymbol(String name, int value) {
    	myName = name;
    	myValue = value;
    }

    public String getName() {
        return myName;
    }

    public int evaluate() {
        return myValue;
    }
    
    public String toString() {
    	return myName;
    }
}