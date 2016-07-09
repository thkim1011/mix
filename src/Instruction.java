/// TODO: COMPLETELY REDO

public class Instruction extends Word {
    private int myCounter;

    public Instruction(String command, APart address, IPart index, FPart field) {
        super(address.evaluate() / Math.abs(address.evaluate()), Math.abs(address.evaluate())/64, Math.abs(address.evaluate())%64, index.getValue(), field.getValue(), Assemble.convertToByte(command));
        myCounter = -1;
    }
    public Instruction(String command, APart address, IPart index, FPart field, int counter) {
    	super(address.evaluate() / Math.abs(address.evaluate()), Math.abs(address.evaluate())/64, Math.abs(address.evaluate())%64, index.getValue(), field.getValue(), Assemble.convertToByte(command));
        myCounter = counter;
    }


    public int getCounter() {
        return myCounter;
    }

    public void execute() {

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