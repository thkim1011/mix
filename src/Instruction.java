// TODO: COMPLETELY REDO

public class Instruction {
	private String myCommand;
    private Expression myAddress;
    private IPart myIndex;
    private FPart myField;
    
    public Instruction(String command, Expression address, IPart index, FPart field) {
    	myCommand = command;
    	myAddress = address;
    	myIndex = index;
    	myField = field;
    }

    public void execute() {

    }

    public String toString() {
    	int addr = myAddress.evaluate();
    	String sign = addr >= 0 ? "+" : "-";
    	addr = Math.abs(addr);
    	return "" + sign + " " + 
    	intToString(addr / 64) + " " +
    	intToString(addr % 64) + " " +
    	intToString(myIndex.getValue()) + " " +
    	intToString(myField.getValue()) + " " +
    	intToString(Assemble.convertToByte(myCommand));
    }
    
    private String intToString(int x) {
    	if(x / 10 == 0) {
    		return "0" + x;
    	}
    	else {
    		return "" + x;
    	}
    }
    
    /*
    TODO: this is directly copy and pasted from Word class. adapt to here.
    
    public void add(Word other) {
        // Find the sign
        int finalSign;
        if(Math.abs(this.getValue()) < Math.abs(this.getValue())) { // Sign is only inverted in this case
            if(this.isPositive() && !other.isPositive() || !this.isPositive() && other.isPositive()) {
                finalSign = other.sign;
            }
        }
        else {
            finalSign = this.sign;
        }

        // Add 
        for(int i = 5; i >= 1; i++) {

        }
    }
    
    public bool isPositive() {
        return sign >= 0;
    }

    in addition, a method selfCorrect is to be run in order to fix the values to a proper word. 
    */
}