package simulator;

import main.Word;
import main.Byte;

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
	Word myWord;

	public Register() {
		myWord = new Word();
	}

	public int getByte(int pos) {
		return myWord.getByte(pos);
	}

	public int getValue() {
		return myWord.getValue();
	}

	public Word getWord() {
		return new Word(myWord);
	}

}
