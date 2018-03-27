package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Constants;
import main.Word;
import main.Byte;
import misc.Pair;

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
    // Counter
    private int myCounter;
    // Symbols
    private Map<String, Integer> myDefinedSymbols;
    private List<FutureReference> myFutureReferences;

    /**
     * The Assemble constructor creates an instance of the Assemble class which
     * contains the methods to assemble a myProgram written in MIXAL to MIX
     * machine code.
     */

    public Assembler() {
        myCounter = 0;
        myDefinedSymbols = new HashMap<>();
        myFutureReferences = new ArrayList<>();

        // Instantiate Local Symbols
        for (int i = 0; i <= 9; i++) {
            myDefinedSymbols.put(i + "B", -1);
        }
    }

    /**
     * Assembles given line of code with the current local defined symbols.
     * @param line is the line to be assembled.
     * @return the assembled line as a Word.
     */
    public Word assemble(String line) {
        String[] tokenizedInst = tokenizeInst(line);
        String loc = tokenizedInst[0];
        String op = tokenizedInst[1];
        String address = tokenizedInst[2];

        if (!loc.equals("")) {
            // Add as a defined symbol
            if (!isSymbol(loc)) {
                throw new IllegalArgumentException("Rule 1: A symbol is a string of one to ten " +
                        "letters/digits, containing at least one letter.");
            }
            addDefinedSymbol(loc, getCounter());
        }

        if (Constants.COMMANDS.containsKey(op)) {
            String[] tokenizedAddr = tokenizeAddr(address);
            // Process Command
            Pair command = Constants.COMMANDS.get(op);

            // Process Parts
            Word w = processAPart(tokenizedAddr[0]);
            int indexPart = processIndexPart(tokenizedAddr[1]);
            int fPart = processFPart(tokenizedAddr[2], command.getField());

            myCounter++;

            w.setByte(3, indexPart);
            w.setByte(4, fPart);
            w.setByte(5, command.getCode());
            return w;
        }

        else if (op.equals("EQU")) {
            // Process address field
            if (!isWValue(address)) {
                throw new IllegalArgumentException("Rule 11b. The ADDRESS should be a WValue.");
            }
            int val = evaluateWValue(address).getValue();
            modifyDefinedSymbol(loc, val);
            return null;
        }

        else if (op.equals("ORIG")) {
            if (!isWValue(address)) {
                throw new IllegalArgumentException("Rule 11c. The ADDRESS should be a WValue.");
            }
            int val = evaluateWValue(address).getValue();
            myCounter = val;
            return null;
        }

        else if (op.equals("CON")) {
            if (!isWValue(address)) {
                throw new IllegalArgumentException("Rule 11d. The ADDRESS should be a WValue.");
            }
            myCounter++;
            return evaluateWValue(address);
        }

        else if (op.equals("ALF")) {
            myCounter++;
            Byte[] word = new Byte[5];
            for(int i = 0; i < 5; i++) {
                word[i] = new Byte(Constants.CHARACTER_CODE.get(address.charAt(i)));
            }
            return new Word(true, word);
        }

        else {
            throw new IllegalArgumentException("Rule 11. OP is not among the" +
                    "six possibilities defined on page 155.");
        }
    }

    /**
     * Tokenize the instruction based on spaces. The special edge case
     * is when the OP is ALF, in which we go by Donald Knuth's rule
     * for interpreting the spaces. Specifically, we have that the
     * "special OP-code ALF is, however, followed either by two blank
     * spaces and five characters of alphameric data, or by a single
     * blank space and five alphameric characters, the first of which
     * is nonblank." The third element of the output should
     * contain the five alphameric characters.
     *
     * @param line is the line (i.e. inst) to be tokenized.
     * @return an array of three elements: LOC, OP, ADDRESS.
     */
    public static String[] tokenizeInst(String line) {
        // Add an empty char to end of line
        String[] tokens = new String[3];
        int i = 0, begin = 0, end;

        for (int part = 0; part < 3; part++) {
            // Compute start and end
            while (i < line.length() && line.charAt(i) != ' ') i++;
            end = i;
            tokens[part] = line.substring(begin, end);
            // If ALF
            if (part == 1 && tokens[1].equals("ALF")) {
                i++;
                if (line.charAt(i) == ' ') i++;
                line += "     ";
                tokens[2] = line.substring(i, i + 5);
                break;
            }
            while (i < line.length() && line.charAt(i) == ' ') i++;
            begin = i;
        }
        return tokens;
    }

    /**
     * Tokenize the address assuming the format of "A, I(F)". This method should
     * return the list {"A", ",I", "(F)"}.
     * @param addr is the address to tokenize.
     * @return the tokens.
     */
    public static String[] tokenizeAddr(String addr) {
        int i = 0;
        int j = 0;

        String[] tokens = new String[3];

        while (i < addr.length() && addr.charAt(i) != ',') i++;
        while (j < addr.length() && addr.charAt(j) != '(') j++;

        if (i == addr.length()) {
            i = j;
        } else if (i > j) {
            throw new IllegalArgumentException("Rule 11(a): ADDRESS should be an" +
                    "A-part, followed by an index part, followed by an " +
                    "F-part.");
        }

        tokens[0] = addr.substring(0, i);
        tokens[1] = addr.substring(i, j);
        tokens[2] = addr.substring(j, addr.length());

        return tokens;
    }


    /**
     * Gets the counter.
     * @return the counter.
     */
    public int getCounter() {
        return myCounter;
    }

    /**
     * Returns whether a symbol is valid or not. A symbol is a
     * string of one to ten letters and /or digits, containing at
     * least one letter.
     * @param name of symbol.
     * @return true or false.
     */
    public static boolean isSymbol(String name) {
        // Check size constraint
        if (1 > name.length() || name.length() > 10) {
            return false;
        }

        // Check each character and ensure at least one letter
        boolean hasLetter = false;

        for (int i = 0 ; i < name.length(); i++){
            if (Character.isLetter(name.charAt(i))) {
                hasLetter = true;
            } else if (!Character.isDigit(name.charAt(i))) {
                return false;
            }
        }

        // Otherwise, return true if it has a letter
        return hasLetter;
    }

    /**
     * Returns whether number is a string of one to ten digits.
     * @param number is a string.
     * @return whether it is a number.
     */
    public static boolean isNumber(String number) {
        if (1 > number.length() || number.length() > 10) {
            return false;
        }

        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Parses number into int.
     * @param number is a string.
     * @return the integer equivalent.
     */
    private static int parseNumber(String number) {
        return Integer.parseInt(number);
    }

    /**
     * Return the defined symbol associated with name.
     * @param name of defined symbol
     * @return value of defined symbol.
     */
    public int getDefinedSymbol(String name) {
        return myDefinedSymbols.get(name);
    }

    /**
     * Adds the defined symbol with value to the map.
     * @param name of symbol to add.
     * @param value of defined symbol.
     */
    public void addDefinedSymbol(String name, int value) {
        if (myDefinedSymbols.containsKey(name)) {
            throw new IllegalArgumentException("Rule 13. Each symbol has one and only one " +
                    "equivalent value.");
        }
        myDefinedSymbols.put(name, value);
    }

    /**
     * The only function to call this method is when EQU is being processed.
     * @param name is the name of symbol.
     * @param value is the value of symbol.
     */
    public void modifyDefinedSymbol(String name, int value) {
        myDefinedSymbols.put(name, value);
    }

    /**
     * Tests if name is a defined symbol.
     * @param name is a symbol.
     * @return true or false.
     */
    private boolean isDefinedSymbol(String name) {
        return isSymbol(name) && myDefinedSymbols.containsKey(name);
    }

    private static boolean isAsterisk(String expr) {
        return expr.equals("*");
    }

    /**
     * Tests is given string is an atomic expression.
     * @param expr is the given string.
     * @return true or false.
     */
    public boolean isAtomicExpression(String expr) {
        return isNumber(expr) || isDefinedSymbol(expr) ||
                isAsterisk(expr);
    }

    /**
     * Parses given atomic expression and returns its value.
     * @param expr is the given string.
     * @return true or false.
     */
    public int parseAtomicExpression(String expr) {
        if (isNumber(expr)) {
            return parseNumber(expr);
        } else if (isDefinedSymbol(expr)) {
            return getDefinedSymbol(expr);
        } else if (isAsterisk(expr)) {
            return getCounter();
        } else {
            throw new IllegalArgumentException("Rule 4. An atomic epxression " +
                    "must be a number, a defined smbol, or an asterisk.");
        }
    }

    private boolean isSign(char c) {
        return c == '+' || c == '-';
    }

    private boolean isBinaryOperation(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") ||
                op.equals("/") || op.equals("//") || op.equals(":");
    }

    /**
     * Returns val1 (op) val2, where op is the operator given by the string.
     * @param val1 is the first operand.
     * @param op is the operator.
     * @param val2 is the second operand.
     * @return the result.
     */
    private int evaluateOperator(int val1, String op, int val2) {
        if (op.equals("+")) {
            return val1 + val2;
        }
        if (op.equals("-")) {
            return val1 - val2;
        }
        if (op.equals("*")) {
            return val1 * val2;
        }
        if (op.equals("/")) {
            return val1 / val2;
        }
        if (op.equals("//")) {
            return 64 * 64 * 64 * 64 * 64 * val1 / val2;
        }
        if (op.equals(":")) {
            return 8 * val1 + val2;
        }

        throw new IllegalArgumentException("Rule 5: Operator is not among the valid six.");
    }

    private int getSign(char c) {
        return c == '+' ? 1 : -1;
    }

    /**
     * Determines if the given string is a valid expression or not.
     * @param expr is the expression.
     * @return true or false.
     */
    public boolean isExpression(String expr) {
        if (expr.equals("")) {
            return false;
        }

        if (isAtomicExpression(expr) ||
                (isSign(expr.charAt(0)) && isAtomicExpression(expr.substring(1)))) {
            return true;
        }

        // Find position of operator
        int i = expr.length() - 1;
        while(!isBinaryOperation(expr.substring(i, i + 1))) {
            i--;
            if (i < 0) {
                return false;
            }
        }

        int j = i + 1;

        if (i > 0 && isBinaryOperation(expr.substring(i - 1, i)) &&
                expr.substring(i - 1, i + 1).equals("//")) i--;
        return isExpression(expr.substring(0, i)) && isBinaryOperation(expr.substring(i, j)) &&
                isAtomicExpression(expr.substring(j));
    }

    /**
     * Parses and evaluates expression with the assumption
     * that the string is a valid expression.
     * @param expr is the expression to be evaluated.
     * @return the value associated with the expression.
     */
    public int evaluateExpression(String expr) {
        if (isAtomicExpression(expr)) {
            return parseAtomicExpression(expr);
        }
        else if (isSign(expr.charAt(0)) && isAtomicExpression(expr.substring(1))) {
            return getSign(expr.charAt(0)) * parseAtomicExpression(expr.substring(1));
        }
        else {
            // Find position of operator
            int i = expr.length() - 2;
            while(!isBinaryOperation(expr.substring(i, i + 1))) i--;
            int j = i + 1;
            if (isBinaryOperation(expr.substring(i - 1, i)) &&
                    expr.substring(i - 1, i + 1).equals("//")) i--;
            return evaluateOperator(evaluateExpression(expr.substring(0, i)),
                    expr.substring(i, j),
                    evaluateExpression(expr.substring(j)));
        }
    }

    /**
     * Given
     * @param str
     * @return
     */
    public Word processAPart(String str) {
        if (str.equals("")) {
            return new Word();
        }
        else if (isExpression(str)) {
            Word w = new Word();
            int eval = evaluateExpression(str);

            w.setSign(eval >= 0);
            w.setByte(1, Math.abs(eval) >> 6);
            w.setByte(2, Math.abs(eval) % 64);

            return w;
        }
        else if (isSymbol(str)) {
            // Handle future reference
            return new Word(str);
        }
        else if (isLiteralConstant(str)) {
            // Handle Literal Constant
            return null;
        }
        else {
            throw new IllegalArgumentException("Rule 6: An A-Part must be either " +
                    "vacuous, an expression, a future reference, or a literal constant.");
        }
    }

    public int processIndexPart(String str) {
        if (str.equals("")) {
            return 0;
        }
        else if (str.charAt(0) == ',' && isExpression(str.substring(1))) {
            return evaluateExpression(str.substring(1));
        }
        else {
            throw new IllegalArgumentException("Rule 7: An index part must be either " +
                    "vacuous or a comma followed by an expression.");
        }
    }

    public int processFPart(String str, int defaultField) {
        if (str.equals("")) {
            return defaultField;
        }
        else if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')' &&
                isExpression(str.substring(1, str.length() - 1))) {
            return evaluateExpression(str.substring(1, str.length() - 1));
        }
        else {
            throw new IllegalArgumentException("Rule 8: An F-part is either vacuous " +
                    "or an expression enclosed in parenthesis.");
        }
    }

    public boolean isFPart(String str) {
        if (str.equals("")) {
            return true;
        }
        else if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')' &&
                isExpression(str.substring(1, str.length() - 1))) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isLiteralConstant(String cons) {
        return false;
    }

    /**
     * Determines whether the string val is a WValue or not.
     * @param val is the string to be parsed.
     * @return is true or false.
     */
    public boolean isWValue(String val) {
        if (val.equals("")) {
            return false;
        }
        // Find index
        int i = 0;
        while(i < val.length() && val.charAt(i) != '(') i++;

        if (isExpression(val.substring(0, i)) && isFPart(val.substring(i))) {
            return true;
        }
        else {
            int j = val.length() - 1;
            while (j >= 0 && val.charAt(j) != ',') j--;
            return j > -1 && isWValue(val.substring(0, j)) &&
                    isWValue(val.substring(j + 1));
        }
    }

    /**
     * Evaluates a wval string into a Word.
     * @param val is the wval to be processed.
     * @return the Word that is the evaluation of the WValue.
     */
    public Word evaluateWValue(String val) {
        // Find separating index
        int i = val.length() - 1;
        while (i >= 0 && val.charAt(i) != ',') i--;

        int j = i + 1;
        while (j < val.length() && val.charAt(j) != '(') j++;
        if (i < 0) {
            return (new Word()).applyWValue(
                    evaluateExpression(val.substring(0, j)),
                    processFPart(val.substring(j), 5));
        }
        return evaluateWValue(val.substring(0, i)).applyWValue(
                evaluateExpression(val.substring(i + 1, j)),
                processFPart(val.substring(j), 5));

    }

    public void updateFutureReferences(String name, int value) {
        for (int i = 0; i < myFutureReferences.size(); i++) {
        }
    }
}