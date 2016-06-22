public class Expression {
    private boolean sign;
    private AtomicExpression value;
    private BinaryOperator op;
    private Expression node;

    public Expression(String exp) {
        int i = 0;
        sign = true;
        if(exp.charAt(0) == '+' || exp.charAt(0) == '-') {
            sign = exp.charAt(0) == '+';
            i++;
        }
        int j = i;
        char temp = exp.charAt(j);
        while(j < exp.length() && temp != '+' && temp != '-' && temp !='*' && temp != '/' && temp != ':') {
            j++;
            temp = exp.charAt(j);
        }
        value = new AtomicExpression(exp.substring(i,j));
        
        if(j == exp.length()) {
            op = null;
            node = null;
        }
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