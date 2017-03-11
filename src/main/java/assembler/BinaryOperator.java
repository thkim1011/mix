package assembly;

//TODO: try to not use a BinaryOperator class.
public class BinaryOperator {
    private String myOp;
    public BinaryOperator(String op) {
        //TODO: add precondition checking here
        myOp = op;
    }

    public int evaluate(int a, int b) {
        switch(myOp) {
            case "+":
            return a + b;
            case "-":
            return a - b;
            case "*":
            return a*b;
            case "/":
            return a/b;
            case "//":
            return a* (int) Math.pow(6,5) / b; //TODO: FIX
            case ":":
            return 8*a + b;
            default:
            	return -1;
        }
    }
}