package assembler;

import assembler.symbol.DefinedSymbol;
import java.util.HashMap;

public interface APart {
    public abstract int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbol);
}