package validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Tae Hyung Kim on 2017-03-11.
 */
public class Validate {
    private TreeSet<String> symbolList;

    public Validate(String filenameIn) throws IOException {
        BufferedReader fileIn = new BufferedReader(new FileReader(filenameIn));
        symbolList = new TreeSet<String>();
    }

    private boolean isValidSymbol(String symbol) {
        return symbol.matches("[0-9A-Z]+") && !symbol.matches("[0-9]+") && symbol.length() <= 10;
    }

    private boolean isValidNumber(String number) {
        return number.matches("[0-9]+") && number.length() <= 10;
    }

    private boolean isValidDefinedSymbol(String symbol) {
        return symbolList.contains(symbol);
    }

    private boolean isValidAsterisk(String asterisk) {
        return asterisk.equals("*");
    }
    public boolean isValidAtomicExpression(String expression) {
        return isValidNumber(expression) || isValidDefinedSymbol(expression) || isValidAsterisk(expression);
    }

    private boolean isValidExpression(String expression) {
        // Check if there is a sign
        if(expression.charAt(0) == '+' || expression.charAt(0) == '-') {
            expression = expression.substring(1);
        }

        String[] partition = expression.substring(1).split("[/+*-]|(//)", 2);
        if(partition.length == 1) {
            return isValidAtomicExpression(partition[0]);
        }
        else {
            return isValidAtomicExpression(partition[0]) && isValidExpression(partition[1]);
        }
    }

    private boolean isValidAPart(String aPart) {
        // Is Vacuous
        if (aPart.equals("")) {
            return true;
        }

        // Is an Expression
        else if (isValidExpression(aPart)) {
            return true;
        }

        // Is a Future Reference
        else if (isValidSymbol(aPart))
            return true;

        // Is a Literal Constant
        else if (isValidLiteralConstant(aPart)) {
            return true;
        }

        else {
            return false;
        }
    }

    private boolean isValidIndexPart(String indexPart) {
        // Is Vacuous
        if (indexPart.equals("")) {
            return true;
        }

        // Is an Expression Preceded by a Comma
        else if (indexPart.charAt(0) == ',' && isValidExpression(indexPart.substring(1))) {
            return true;
        }

        else {
            return false;
        }
    }

    private boolean isValidFPart(String fPart) {
        //Is Vacuous
        if (fPart.equals("")) {
            return true;
        }

        // Is an Expression closed by Parentheses
        else if (fPart.charAt(0) == '(' && fPart.charAt(fPart.length() - 1) == ')' && isValidExpression(fPart.substring(1, fPart.length() - 1))) {
            return true;
        }

        else {
            return false;
        }
    }

    private boolean isValidWValue(String WValue) {
        int position = WValue.indexOf(',');

        if(position == -1) {
            int fPart = WValue.indexOf("(");
            if (fPart == -1) {
                return isValidExpression(WValue);
            }
            else {
                return isValidExpression(WValue.substring(0, fPart)) && isValidFPart(WValue.substring(fPart));
            }
        }

        else {
            return isValidWValue(WValue.substring(0, position)) && isValidWValue(WValue.substring(position+1));
        }
    }

    private boolean isValidLiteralConstant(String literalConstant) {
        return literalConstant.charAt(0) == '=' &&
                literalConstant.charAt(literalConstant.length() - 1) == '=' &&
                isValidWValue(literalConstant.substring(1, literalConstant.length() - 1));
    }

}
