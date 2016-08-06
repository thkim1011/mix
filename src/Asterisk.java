public class Asterisk implements AtomicExpression {
	private int value;
	
	public Asterisk() {
		value = Assemble.counter;
	}
	
    public int evaluate() {
        return value;
    }
    
    public String toString() {
    	return "*";
    }
}