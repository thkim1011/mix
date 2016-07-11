public class Number implements AtomicExpression {
    private int myNumber; 

    public Number(int number) {
        myNumber = number;
    }

    public int evaluate() {
        return myNumber;
    }
    
    public String toString() {
    	return "" + myNumber;
    }
}