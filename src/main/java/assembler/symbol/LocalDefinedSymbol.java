package assembler.symbol;

/**
 * Created by Tae Hyung Kim on 2017-03-10.
 */
public class LocalDefinedSymbol implements Symbol {
    private String myName;
    private int myValue;

    public LocalDefinedSymbol(String name, int value) {
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
