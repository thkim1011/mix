package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assembler.expression.Expression;
import assembler.symbol.LocalDefinedSymbol;
import main.Constants;
import main.Word;
import main.Byte;
import assembler.symbol.DefinedSymbol;
import assembler.symbol.FutureReference;
import assembler.symbol.Symbol;

/**
 * <b>Assemble class.</b> This implementation of MIX consists of three
 * components: the assembler, the debugger, and the simulator. This class acts
 * to perform the operations of the assembler. In order to use this class, an
 * instance of an assembler must be created first using the constructor, and
 * then the assemble method must be run on each individual line.
 *
 * @author Tae Hyung Kim
 */

public class Assembler {
    // Counter and Byte Size
    private int myCounter;
    // Symbols
    private Map<String, DefinedSymbol> myDefinedSymbols;
    private List<FutureReference> myFutureReferences;
    private List<FutureReference> myLocalFutureReferences;
    private List<LocalDefinedSymbol> myLocalDefinedSymbols;

    /**
     * The Assemble constructor creates an instance of the Assemble class which
     * contains the methods to assemble a myProgram written in MIXAL to MIX
     * machine code.
     */

    public Assembler(int byteSize) {
        myCounter = 0;
        myDefinedSymbols = new HashMap<>();
        myFutureReferences = new ArrayList<>();
        myLocalDefinedSymbols = new ArrayList<>();
        myLocalFutureReferences = new ArrayList<>();

        // Instantiate Local Symbols
        for (int i = 0; i <= 9; i++) {
            myDefinedSymbols.put(i + "B", new DefinedSymbol(i + "B", -1));
        }
    }

    /**
     * Assembles given line of code with the current local defined symbols.
     * @param line is the line to be assembled.
     * @return the assembled line as a Word.
     */
    private Word assemble(String line) {
        return null;
    }

    /**
     * Gets the counter.
     * @return the counter.
     */
    public int getCounter() {
        return myCounter;
    }

    /**
     * Return the defined symbol associated with name.
     * @param name of defined symbol
     * @return value of defined symbol.
     */
    public int getDefinedSymbols(String name) {
        return myDefinedSymbols.get(name).getValue();
    }

}