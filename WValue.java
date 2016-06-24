/**
* A WValue is an expression followed by an F-part or a WValue followed by one. 
*/

public class WValue {
    private Expression myExp;
    private FPart myField;
    private WValue nextNode;

    public WValue(String word) {
        int pos = word.find(",");
        boolean isAggregate = pos != -1;
        String part;

        // FInd the part to focus on and separate the 
        // rest for recursive processing by WValue constructor
        if(isAggregate) {
            part = word.substring(0, pos);
            nextNode = new WValue(word.substring(pos + 1));
        }
        else {
            part = word;
            nextNode = null;
        }
        if(word.indexOf("(") != -1) {
            myExp = new Expression(word.substring());
            myField = null;
        }
        else {
            myExp = new Expression(word.substring(0, word.indexOf("(")));
            myField = new FPart(word.substring(word.indexOf("(")));
        }
    }

    public WValue(int loc) {
        myExp = new Expression(loc);
        myField = null;
        nextNode = null;
    }

    public Expression getExpression() {
        return myExp;
    }

    public Field getField() {
        return myField;
    }

    public WValue getNextNode() {
        return nextNode;
    }

    public int evaluate() {
        if(nextNode == null) {
            return myExp.evaluate();
        }
        else {
            return myExp.evaluate() + nextNode.evaluate();
        }
    }
}