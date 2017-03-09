package misc;

/**
 * <b>Pair class.</b> This class represents a pair of two integers. In this
 * specific case, the first integer is the operation code and the second integer
 * is the default field, and the methods are named accordingly.
 * 
 * @author Tae Hyung Kim
 *
 */

public class Pair {
	int myCode;
	int myField;

	public Pair(int code, int field) {
		myCode = code;
		myField = field;
	}

	public int getCode() {
		return myCode;
	}
	
	public int getField() {
		return myField;
	}
}
