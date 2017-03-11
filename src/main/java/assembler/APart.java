package assembly;

import assembly.symbol.DefinedSymbol;
import java.util.HashMap;

public interface APart {
    public abstract int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbol);
}