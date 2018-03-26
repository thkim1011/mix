package simulator.register;

import word.Word;
import word.Byte;

/**
 * <b>Register class.</b> The simulator.register class serves to be an abstraction of the
 * idea of a simulator.register.
 * 
 * The purpose for creating such an interface is for simplification of the
 * loading operator and storing operator, because, without it, at least one
 * method is required for every single command that could honestly be classified
 * into one method. I figured that this would be VERY bad for debugging in the
 * future.
 * 
 * The primary two methods that this class must have in order to serve its
 * purpose are getWord, which converts the simulator.register into a Word object using a
 * given field, and setRegister, which takes a Word object as a input, and will
 * be incorporated into the simulator.register.
 * 
 * TODO: This comment is outdated.
 * @author Tae Hyung Kim
 */

public class Register {
	private boolean mySign;
	private Byte[] myBytes;
	final private int mySize;

	public Register(int size) {

		mySign = true;
		myBytes = new Byte[size];
		for (int i = 0; i < size; i++) {
			myBytes[i] = new Byte(0);
		}
		mySize = size;
	}

	public Word getWord() {
		return new Word(mySign, myBytes);
	}

	public void setRegister(Word in) {
		mySign = in.getSign();
		int i;
		for(i = 5; i >= 1; i--) {
			if(i >= 6 - mySize) {
				// myBytes[i-1] = in.getByte(i);
			}
			else {
				if(myBytes[i-1].getValue() != 0) {
					throw new IllegalArgumentException("Inexistent bytes of the simulator.register were attempted to be written.");
				}
			}
		}
	}

	public int getValue() {
		int value = 0;
		for(int i = 0; i < mySize; i++) {
			value *= 64;
			value += myBytes[i].getValue();
		}
		return value;
	}
}
