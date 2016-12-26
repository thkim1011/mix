package main;

import assembly.Assemble;
import assembly.APart;
import assembly.FPart;
import assembly.IPart;
import register.Register;

/**
 * <b>Word class.</b> This class is rather universally used within the project,
 * so I decided to add this to the main package. Also, this is my first time
 * trying to use packages, so it may not be very well organized(?). Anyways, the
 * Word class is used to store a MIX word as Donald Knuth describes it: A
 * <i>computer word</i> consists of five bytes and a sign. The sign portion has
 * only two possible values, + and -.
 * 
 * There's quite a lot of constructors for this class, but they can be divided
 * into general groups. The first group, which only contains one, is a
 * constructor that essentially takes an instruction. Of course, it is given as
 * broken pieces, but that's the idea. The second group of constructors consist
 * of converting from int to Byte and storing each in the myBytes. The third
 * simply takes in Byte objects as arguments and use those (and I'm allowed to
 * do this because the Byte class is immutable). The final only consists of the
 * default constructor. 12/24/16
 * 
 * TODO (2016/12/24): Find out if it's good idea to have a instance variable
 * that is dependent on other instance variable. For example, in this case,
 * myValue solely depends on mySign and myBytes.
 * 
 * @author Tae Hyung Kim
 */
public class Word {

	private boolean mySign;
	private Byte[] myBytes;

	/**
	 * <b>Constructor for Word Class.</b> This constructor takes in the normal
	 * parts of a MIX command and stores it as a Word object. This constructor
	 * is primarily used for the assembly process.
	 * 
	 * TODO: I need to figure out how java deals with signs when dividing so I
	 * can do something about the call to Math.abs.
	 */
	public Word(String command, APart address, IPart index, FPart field) {
		myBytes = new Byte[5];
		int addr = address.evaluate();
		mySign = addr >= 0;
		addr = Math.abs(addr);
		myBytes[1] = new Byte(addr / 64);
		myBytes[2] = new Byte(addr % 64);
		myBytes[3] = new Byte(index.getValue());
		myBytes[4] = new Byte(field.getValue());
		myBytes[5] = new Byte(Assemble.convertToByte(command));
	}

	/**
	 * <b>Constructor for Word Class.</b> This constructor takes one boolean
	 * value representing the sign of the Word object and five integers
	 * representing the five Bytes of the Word object.
	 */
	public Word(boolean a0, int a1, int a2, int a3, int a4, int a5) {
		myBytes = new Byte[5];
		mySign = a0;
		myBytes[0] = new Byte(a1);
		myBytes[1] = new Byte(a2);
		myBytes[2] = new Byte(a3);
		myBytes[3] = new Byte(a4);
		myBytes[4] = new Byte(a5);
	}

	/**
	 * <b>Constructor for Word class.</b> This was necessary due to the
	 * existence of both positive and negative zero in the MIX language.
	 * 
	 * TODO: Update this comment and the code is ugly :(
	 */
	public Word(int sign, int x) {
		if (!(-1073741823 <= x && x < 1073741823)) {
			throw new IllegalArgumentException("x must be greater than -64^5 and less than 64^5");
		}
		myBytes = new Byte[5];
		mySign = x >= 0;
		x = Math.abs(x);
		for (int i = 5; i >= 1; i--) {
			myBytes[i] = new Byte(x % 64);
			x = x / 64;
		}
	}

	// TODO: check if this constructor works properly
	// TODO: write a formal constructor javadoc (is that what it is?) comment
	// specifying that null parts of myBytes do not modify anything in
	// setAllBytes (which is actually pretty useful).
	public Word(int sign, int x, FPart field) {
		this(sign, x);
		// Check if field is valid TODO: Maybe make this into a method?
		int left = field.getLeft();
		int right = field.getRight();

		// TODO: IMO the following code should've been done when the FPart was
		// created.
		if (!(0 <= left && left <= 5) || !(0 <= right && right <= 5) || left > right) {
			throw new IllegalArgumentException("Field value is invalid");
		}

		// Set unspecified positions to null;
		for (int i = 0; i <= 5; i++) {
			if (!(left <= i && i <= right)) {
				myBytes[i] = null; // TODO: err... preferably change this to
									// "new Byte(0)"
			}
		}
	}

	public Word(boolean sign, Byte[] bytes) {
		myBytes = new Byte[5];
		mySign = sign;
		for (int i = 0; i < 5; i++) {
			myBytes[i] = bytes[i];
		}
	}

	public Word(boolean sign, Byte b1, Byte b2, Byte b3, Byte b4, Byte b5) {
		mySign = sign;
		myBytes = new Byte[5];
		myBytes[0] = b1;
		myBytes[1] = b2;
		myBytes[2] = b3;
		myBytes[3] = b4;
		myBytes[4] = b5;
	}

	/**
	 * <b>Default constructor for Word class.</b> Not much description is
	 * needed, I think.
	 */
	public Word() {
		mySign = true;
		myBytes = new Byte[5];
		for (int i = 0; i < 5; i++) {
			myBytes[i] = new Byte(0);
		}
	}

	/**
	 * Implementation of toString() from Object
	 * 
	 * @return A String consisting of the sign and the five bytes separated by
	 *         spaces.
	 */
	public String toString() {
		return (mySign ? "+" : "-") + " " + myBytes[0] + " " + myBytes[1] + " " + myBytes[2] + " " + myBytes[3] + " "
				+ myBytes[4];
	}

	/**
	 * getValue()
	 * 
	 * @return The value of this word
	 */
	public int getValue() {
		int value = 0;
		for (int i = 0; i < 5; i++) {
			value *= 64;
			value += myBytes[i].getValue();
		}
		return value;
	}

	/**
	 * <b>getByte method.</b> Something to note about this method is that the
	 * position is starts at 1 rather than at 0 like a normal array (how ironic?
	 * don't we normally say the opposite? lol).
	 */
	public Byte getByte(int pos) {
		// Precondition checking
		if (!(1 <= pos && pos <= 5)) {
			throw new IllegalArgumentException("The given position is invalid.");
		}
		return myBytes[pos - 1];
	}

	public Word getCopy() {
		return new Word(mySign, myBytes[0], myBytes[1], myBytes[2], myBytes[3], myBytes[4]);
	}

	public boolean getSign() {
		return mySign;
	}

	public void setByte(int pos, Byte value) {
		// Precondition checking
		if (!(0 <= pos && pos <= 5)) {
			throw new IllegalArgumentException("The given position is invalid.");
		}
		myBytes[pos] = value;
		// TODO: which is better.. create a new byte or modify the existing?
	}

	/**
	 * <b>setAllBytes method.</b> This method takes another Word object and
	 * essentially sets all Bytes and sign of this Word object to the other Word
	 * object. This uses a hack that treats null elements as having no effect on
	 * the existing Byte, and such null (infested?) Words can be constructed
	 * through the Word(int, int, FPart) ctor. I'm not exactly sure if this is a
	 * good way of doing this, so I'll probably get rid of it if I can find a
	 * better method to deal with this.
	 */
	public void setAllBytes(Word other) {
		mySign = other.mySign;
		for (int i = 0; i <= 5; i++) {
			if (other.myBytes[i] != null) {
				this.myBytes[i] = other.myBytes[i];
			}
		}
	}

	public void setSign(boolean sign) {
		mySign = sign;
	}

	/**
	 * <b><code>LDr</code> method.</b> This method is equivalent to the action
	 * of LDA, LDX, LD1, etc., of MIXAL. (12/24/16)
	 */
	private void LDr(Register reg) {
		Word w = computeV();
		reg.setRegister(w);
	}

	/**
	 * <b>LDrN method.</b> This method is equivalent to the action of LDAN,
	 * LDXN, LD1N, etc., of MIXAL. (12/24/16)
	 */
	private void LDrN(Register reg) {
		Word w = computeV();
		w.setSign(!w.getSign());
		reg.setRegister(w);
	}

	/**
	 * <b><code>STr</code> method.</b> This method is equivalent to the action
	 * of STA, STX, ST1, etc., of MIXAL. (12/24/16)
	 */
	private void STr(Register reg) {
		Word w = reg.getWord();
		int field = myBytes[3].getValue();
		int left = field / 8;
		int right = field % 8;
		int M = computeM();
		for (int i = 5, j = right; j >= left; i--, j--) {
			MIX.memory[M].setByte(i, w.getByte(j));
		}
	}

	/**
	 * <b><code>STZ</code> method.</b> This method is equivalent to the action
	 * of STZ of MIXAL. I initially thought about simply creating a register
	 * stored with 0 and using the STr method, but I figured that would be too
	 * contrived. I might change my mind though because the code is literally
	 * the same thing. (12/24/16)
	 */
	private void STZ() {
		Word w = new Word();
		int field = myBytes[3].getValue();
		int left = field / 8;
		int right = field % 8;
		int M = computeM();
		for (int i = 5, j = right; j >= left; i--, j--) {
			MIX.memory[M].setByte(i, w.getByte(j));
		}
	}

	/**
	 * <b><code>ADD</code> method.</b> This method is equivalent to the ADD
	 * operator in MIXAL. (12/24/16)
	 */
	private void ADD() {
		Word V = computeV();
		Word W = MIX.rA.getWord();
		boolean overflow = false;
		for (int i = 5; i >= 1; i--) {
			int sum = V.getByte(i).getValue() + W.getByte(i).getValue();
			if(overflow) {
				sum += 1;
			}
			if(sum >= 64) {
				overflow = true;
				sum -= 64;
			}
			else {
				overflow = false;
			}
			W.setByte(i, new Byte(sum));
		}
		MIX.overflowToggle = overflow;
		MIX.rA.setRegister(W);
	}

	public void execute() {
		switch (myBytes[4].getValue()) {
		case 1: 
			ADD();
			break;
		case 8:
			LDr(MIX.rA);
			break;
		case 9:
			LDr(MIX.rI[0]);
			break;
		case 10:
			LDr(MIX.rI[1]);
			break;
		case 11:
			LDr(MIX.rI[2]);
			break;
		case 12:
			LDr(MIX.rI[3]);
			break;
		case 13:
			LDr(MIX.rI[4]);
			break;
		case 14:
			LDr(MIX.rI[5]);
			break;
		case 15:
			LDr(MIX.rX);
			break;
		case 16:
			LDrN(MIX.rA);
			break;
		case 17:
			LDrN(MIX.rI[0]);
			break;
		case 18:
			LDrN(MIX.rI[1]);
			break;
		case 19:
			LDrN(MIX.rI[2]);
			break;
		case 20:
			LDrN(MIX.rI[3]);
			break;
		case 21:
			LDrN(MIX.rI[4]);
			break;
		case 22:
			LDrN(MIX.rI[5]);
			break;
		case 23:
			LDrN(MIX.rX);
			break;
		case 24:
			STr(MIX.rA);
			break;
		case 25:
			STr(MIX.rI[0]);
			break;
		case 26:
			STr(MIX.rI[1]);
			break;
		case 27:
			STr(MIX.rI[2]);
			break;
		case 28:
			STr(MIX.rI[3]);
			break;
		case 29:
			STr(MIX.rI[4]);
			break;
		case 30:
			STr(MIX.rI[5]);
			break;
		case 31:
			STr(MIX.rX);
			break;
		case 32:
			STr(MIX.rJ);
			break;
		case 33:
			STZ();
			break;
		}
	}

	/**
	 * <b>computeM method.</b> This method is used to find the value of M that
	 * is commonly referenced in the book as the address indexed by the given
	 * index. (12/24/16)
	 */
	private int computeM() {
		return (mySign ? 1 : -1) * (myBytes[0].getValue() * 64 + myBytes[1].getValue())
				+ MIX.rI[myBytes[2].getValue()].getValue();
	}

	/**
	 * <b>computeV method.</b> In TAoCP, the use of the letter V to denote the
	 * specified field of CONTENTS(M) is mentioned. Since this is used in four
	 * different operators (namely ADD, SUB, MUL, and DIV), I created a separate
	 * method to compute this word. In addition, this allowed me to simplify
	 * both the LDr and LDrN methods. (12/24/16)
	 * 
	 */
	private Word computeV() {
		Word w = MIX.memory[computeM()].getCopy();
		int field = myBytes[3].getValue();
		int left = field / 8;
		int right = field % 8;
		if (left != 0) {
			w.setSign(true);
		}
		for (int i = right, j = 5; j >= 1; i--, j--) {
			if (i >= left) {
				w.setByte(j, w.getByte(i));
			}
			else {
				w.setByte(j, new Byte(0));
			}
		}
		return w;
	}
}