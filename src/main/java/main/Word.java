package main;

import assembler.FutureReference;
import assembler.LiteralConstant;

/**
 * <b>Word class.</b> The Word class is used to store a MIX word as Donald Knuth describes it: A
 * <i>computer word</i> consists of five bytes and a sign. The sign portion has
 * only two possible values, + and -.
 *
 * @author Tae Hyung Kim
 */
public class Word {

    private Sign mySign;
    private Byte[] myBytes;

    private boolean isFuture;
    private String myName;

    /**
     * <b>Constructor for Word Class.</b> This constructor takes one boolean
     * value representing the sign of the Word object and five integers
     * representing the five Bytes of the Word object.
     */
    public Word(boolean a0, int a1, int a2, int a3, int a4, int a5) {
        myBytes = new Byte[5];
        mySign = new Sign(a0);
        myBytes[0] = new Byte(a1);
        myBytes[1] = new Byte(a2);
        myBytes[2] = new Byte(a3);
        myBytes[3] = new Byte(a4);
        myBytes[4] = new Byte(a5);
    }

    public Word(int val) {
        mySign = new Sign(val >= 0);
        myBytes = new Byte[5];
        val = Math.abs(val);
        myBytes[4] = new Byte(val % 64);
        val /= 64;
        myBytes[3] = new Byte(val % 64);
        val /= 64;
        myBytes[2] = new Byte(val % 64);
        val /= 64;
        myBytes[1] = new Byte(val % 64);
        val /= 64;
        myBytes[0] = new Byte(val);
    }

    public Word(boolean sign, Byte[] bytes) {
        myBytes = new Byte[5];
        mySign = new Sign(sign);
        for (int i = 0; i < 5; i++) {
            myBytes[i] = bytes[i];
        }
    }

    /**
     * <b>Default constructor for Word class.</b> Assigns the sign as
     * positive (or true, in the context of this
     * program), and sets all bits to 0.
     */
    public Word() {
        mySign = new Sign(true);
        myBytes = new Byte[5];
        for (int i = 0; i < 5; i++) {
            myBytes[i] = new Byte(0);
        }
    }

    /**
     * Constructor for future words.
     * @param name is the name of the FutureReference.
     */
    public Word(String name) {
        this();
        isFuture = true;
        myName = name;
    }

    /**
     * Constructs a copy of word.
     * @param w
     */
    public Word(Word w) {
        this(w.getSign(), w.getByte(1),
                w.getByte(2),
                w.getByte(3),
                w.getByte(4),
                w.getByte(5));
    }

    /**
     * Implementation of toString() from Object
     *
     * @return A String consisting of the sign and the five bytes separated by
     * spaces.
     */
    public String toString() {
        return mySign.toString()
                + " " + myBytes[0]
                + " " + myBytes[1]
                + " " + myBytes[2]
                + " " + myBytes[3]
                + " " + myBytes[4];
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
        return (mySign.getSign() ? 1 : -1) * value;
    }

    /**
     * <b>getByte method.</b> Something to note about this method is that the
     * position is starts at 1 rather than at 0 like a normal array (how ironic?
     * don't we normally say the opposite? lol).
     */
    public int getByte(int pos) {
        // Precondition checking
        if (!(1 <= pos && pos <= 5)) {
            throw new IllegalArgumentException("The given position is invalid.");
        }
        return myBytes[pos - 1].getValue();
    }

    public boolean getSign() {
        return mySign.getSign();
    }

    public void setByte(int pos, int value) {
        // Precondition checking
        if (!(1 <= pos && pos <= 5)) {
            throw new IllegalArgumentException("The given position is invalid.");
        }
        myBytes[pos-1] = new Byte(value);
        // TODO: which is better.. create a new byte or modify the existing?
    }

    public void setSign(boolean sign) {
        mySign = new Sign(sign);
    }

    public FutureReference getFutureReference() {
        return new FutureReference(myName, mySign, myBytes[0], myBytes[1]);
    }

    public LiteralConstant getLiteralConstant(String wval) {
        return new LiteralConstant(myName, wval, mySign, myBytes[0], myBytes[1]);
    }
    @Override
    public boolean equals(Object other) {
        Word w = (Word) other;
        return this.getSign() == w.getSign() &&
                this.getByte(1) == w.getByte(1) &&
                this.getByte(2) == w.getByte(2) &&
                this.getByte(3) == w.getByte(3) &&
                this.getByte(4) == w.getByte(4) &&
                this.getByte(5) == w.getByte(5);
    }

    /**
     * This is pretty hacky so fix later.
     * @param expr
     * @param field
     * @return
     */
    public Word applyWValue(int expr, int field) {
        Word toApply = new Word(expr);
        if (!isValidField(field)) {
            throw new IllegalArgumentException("Rule 9. Each F_i must have the form 8L + R where " +
                    "0 <= L <= R <= 5.");
        }
        int left = field / 8;
        int right = field % 8;

        // Handle sign
        if(left == 0) {
            setSign(toApply.getSign());
            left++;
            if (right == 0) {
                return this;
            }
        }

        for (int i = left; i <= right; i++) {
            setByte(i, toApply.getByte(i - right + 5));
        }
        return this;
    }

    public static boolean isValidField(int field) {
        int left = field / 8;
        int right = field % 8;
        return 0 <= left && left <= right && right <= 5;
    }

    public int getValueByField(int field) {
        if (!isValidField(field)) {
            throw new IllegalArgumentException("Field is not valid.");
        }
        int left = field / 8;
        int right = field % 8;
        int value = 0;
        int sign = 1;

        if (left == 0) {
            sign = mySign.getSign() ? 1 : -1;
            left++;
        }

        for (int i = left; i <= right; i++) {
            value *= 64;
            value += getByte(i);
        }

        return sign * value;
    }

    public int getAddress() {
        return getValueByField(2);
    }

    public int getIndex() {
        return getByte(3);
    }

    public int getCommand() {
        return getByte(5);
    }

    public int getField() {
        return getByte(4);
    }

    public static Word add(Word w1, Word w2) {
        int sum = w1.getValue() + w2.getValue();
        Word w = new Word(sum);
        if (sum == 0) {
            w.setSign(w1.getSign());
        }
        return w;
    }

    public static Word negate(Word w) {
        w = new Word(w);
        w.setSign(!w.getSign());
        return w;
    }

    public static Word multiply(Word w1, Word w2) {
        int prod = (w1.getValue() * w2.getValue()) % (64 * 64 * 64 * 64 * 64);
        boolean sign = w1.getSign() == w2.getSign();
        Word w = new Word(prod);
        w.setSign(sign);
        return w;
    }

    public static Word upperMultiply(Word w1, Word w2) {
        long prod = ((long) w1.getValue() * (long) w2.getValue()) / (64 * 64 * 64 * 64 * 64);
        boolean sign = w1.getSign() == w2.getSign();
        Word w = new Word((int) prod);
        w.setSign(sign);
        return w;
    }
}