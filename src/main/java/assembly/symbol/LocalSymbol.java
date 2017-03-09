package assembly.symbol;

import assembly.Assemble;

/**
 * <b>LocalSymbol class.</b> This class is an abstraction of a local symbol in MIXAL. 
 * @author Tae Hyung Kim
 *
 */
public class LocalSymbol {
    private int myCounter;

    public LocalSymbol() {
        myCounter = -1;
    }
    
    public void setCounter(int counter) {
    	myCounter = counter;
    }
    
    public int getCounter() {
    	return myCounter;
    }
}