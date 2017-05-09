/**
* A WValue is an expression followed by an F-part or a WValue followed by one. 
*/
package assembler;
import assembler.expression.Expression;
import main.Word;

public class WValue {
    private Expression myExp;
    private FPart myField;
    private WValue nextNode;

    public WValue(String addr) {
        int pos = addr.indexOf(",");

        // Look for part
        if(pos != -1) {
            nextNode = new WValue(addr.substring(pos + 1)); // Process recursively
            addr = addr.substring(0, pos);
        }
        else {
            nextNode = null;
        }
        
        // Look for field
        pos = addr.indexOf("(");
        if(pos != -1) { // If yes field
            myExp = new Expression(addr.substring(0, pos));
            myField = new FPart(addr.substring(addr.indexOf("(")));
        }
        else { // Otherwise
        	myExp = new Expression(addr);
            myField = null; // Null fields represent (0:5) always
        }
    }
    
    /**
     * Get Expression
     * @return Returns an expression to which this represents
     */
    public Expression getExpression() {
        return myExp;
    }
    
    /**
     * Get Field
     * @return Returns an FPart
     */
    public FPart getField() {
        return myField;
    }

    /**
     * Get next node
     * @return Returns the next node to which this points to
     */
    public WValue getNextNode() {
        return nextNode;
    }
   
    public Word evaluate(Assemble assembler) {
        int i = this.myExp.evaluate(assembler);
        int sign = i >= 0 ? 1 : -1;
        Word w;
        if(myField == null) {
        	w = new Word(sign, Math.abs(i));
        }
        else {
        	w = new Word(sign, Math.abs(i), myField, assembler);
        }
        if(nextNode != null) {
            w.setAllBytes(nextNode.evaluate(assembler));
        }
        return w;
    }
}