package assembler.atom;

import assembler.Assemble;
import assembler.symbol.DefinedSymbol;
import java.util.HashMap;

public interface AtomicExpression {
    int evaluate(Assemble assembler);
}