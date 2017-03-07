package assembly;

public class LocalSymbol {
    private String mySymbol;
    private int myCounter;

    public LocalSymbol(String symbol) {
        mySymbol = symbol;
        myCounter = Assemble.counter;
    }
}