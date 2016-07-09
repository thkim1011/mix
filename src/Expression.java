public class Expression {
	
	// Instance Variables
    private int sign;
    private AtomicExpression value;
    private BinaryOperator op;
    private Expression node; // Linked List Structure

    /**
     * Constructor - Takes a String exp and returns 
     * @param exp
     */
    public Expression(String exp) {
        int i = 0;

        // Check for the sign if it exists
        if(exp.charAt(0) == '+' || exp.charAt(0) == '-') {
            sign = (exp.charAt(0) == '+') ? 1 : -1;
            i++;
        }
        int j = i;
        char temp;

        // Increment j until the character is an operator
        do {
        	temp = exp.charAt(j);
        	j++;
        }while(j < exp.length() && temp != '+' && temp != '-' && temp !='*' && temp != '/' && temp != ':');
       
        // This is a new atomic expression
        String atom = exp.substring(i,j);
        
        // Test if number
        boolean isNumber = true;
        for(int k = 0; k < atom.length(); k ++ ){
    		if(!(isNumber(atom.charAt(k)))) {
    			isNumber = false;
    			break;
    		}
    	}
        if(atom.length() == 0) {
        	isNumber = false;
        }
        //If asterisk
        if(atom.equals("*")) {
        	value = new Asterisk();
        }
        
        else if(isNumber) {
        	value = new Number(Integer.parseInt(atom));
        }
        
        else {
        	for(int k = 0; k < Assemble.dsymbols.size(); k++) {
        		if(Assemble.dsymbols.get(k).getName().equals(atom)) {
        			value = Assemble.dsymbols.get(k);
        		}
        	}
        }
        // If at the end of string, then there is no op or node
        if(j == exp.length()) {
            op = null;
            node = null;
        }

        // Otherwise construct a new Expression and attach to this
        else {
            if(temp == '/' && exp.charAt(j+1) == '/') {
                op = new BinaryOperator("//");
                node = new Expression(exp.substring(j+2));
            }
            else {
                op = new BinaryOperator(exp.substring(j,j+1));
                if(exp.substring(j+1).equals("")) {
                	node = null;
                }
                else {
                	node = new Expression(exp.substring(j+1));
                }
                
            }
        }
    }

    /**
     * isNumber private method
     * @param c Character to be checked
     * @return Returns true if is a number and false otherwise
     */
    private boolean isNumber(char c) {
    	return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
    }
    
    public int evaluate() {
        if(node == null ) {
            return value.evaluate();
        }
        else {
            return op.evaluate(sign * value.evaluate(), node.evaluate());
        }
    }
}