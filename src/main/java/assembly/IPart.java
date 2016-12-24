package assembly;

public class IPart {
	private int myIndex;
	
    public IPart() {
        myIndex = 0;
    }

    public IPart(String index) {
    	if(index.length() == 0) {
    		myIndex = 0;
    	}
    	else {
    		if(index.charAt(0) != ',') {
    			throw new IllegalArgumentException("Indices must begin with a comma");
    		}
    		else {
    			myIndex = new Expression(index.substring(1)).evaluate();
    		}
    	}
    }
    
    public int getValue() {
    	return myIndex;
    }
}