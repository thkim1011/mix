package assembly.atom;

import assembly.symbol.DefinedSymbol;
import java.util.HashMap;

public interface AtomicExpression {
    int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbols);
}