public class DefinedSymbol implements Symbol, AtomicExpression {
    private String myName;
    private int myValue;

    public DefinedSymbol(String name) {
        myName = name;
        myValue = Assemble.counter;
    }

    public String getName() {
        return myName;
    }

    public int evaluate() {
        return myValue;
    }
}