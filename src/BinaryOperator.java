public class BinaryOperator {
    private String myOp;
    public BinaryOperator(String op) {
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
            return a* (int) Math.pow(Byte.bytesize,5) / b;
            case ":":
            return 8*a + b;
            default:
            	return -1;
        }
    }
}