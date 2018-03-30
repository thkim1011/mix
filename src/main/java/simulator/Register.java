package simulator;

import main.Word;
import main.Byte;

/**
 * <b>Register class.</b>
 *
 * @author Tae Hyung Kim
 */

public class Register {
	private Word myWord;

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

	public boolean getSign() {
		return myWord.getSign();
	}

	public void setByte(int pos, int val) {
		myWord.setByte(pos, val);
	}

	public void setSign(boolean sign) {
		myWord.setSign(sign);
	}

	public void setWord(Word w) {
	    myWord = new Word(w);
    }
}
