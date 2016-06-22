public class Symbol extends AtomicExpression{
    String myName;
    int myValue;
    public Symbol(String name, int value) {
        myName = name;
        myValue = value;
    }

    public String getName() {
        return myName;
    }

    public int getValue() {
        return myValue;
    }
}