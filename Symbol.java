public class Symbol {
    String myName;
    WValue myValue;
    public Symbol(String name, WValue value) {
        myName = name;
        myValue = value;
    }

    public String getName() {
        return myName;
    }

    public WValue getValue() {
        return myValue;
    }
}