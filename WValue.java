/**
* A WValue is an expression followed by an F-part or a WValue followed by one. 
*/

public class WValue {
    private Expression myExp;
    private Field myField;
    private WValue nextNode;

    public WValue(String word) {
        int pos = word.find(",");
        boolean isAggregate = pos != -1;
        String part;
        if(isAggregate) {
            part = word.substring(0, pos);
            nextNode = new Expression(word.substring(pos + 1));
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
            myField = new Field(word.substring(word.indexOf("(")));
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

    public Word evaluate() {
        
    }
}