public class Expression {
    private boolean sign;
    private AtomicExpression value;
    private BinaryOperator op;
    private Expression node; // Linked List Structure

    public Expression(String exp) {
        int i = 0;
        sign = true;

        // Check for the sign if it exists
        if(exp.charAt(0) == '+' || exp.charAt(0) == '-') {
            sign = exp.charAt(0) == '+';
            i++;
        }
        int j = i;
        char temp = exp.charAt(j);

        // Increment j until the character is an operator
        while(j < exp.length() && temp != '+' && temp != '-' && temp !='*' && temp != '/' && temp != ':') {
            j++;
            temp = exp.charAt(j);
        }

        // This is a new atomic expression
        value = new AtomicExpression(exp.substring(i,j));
        
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
                node = new Expression(exp.substring(j+1));
            }
        }
    }

    public int evaluate() {
        if(sign) {
            return op.evaluate(value.evaluate(), node.evaluate());
        }
    }
}