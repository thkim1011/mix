package simulator;

import main.Word;

public class Simulator {

    private Register rA;
    private Register rX;
    private Register[] rI;
    private Register rJ;

    private Word[] myMemory;

    private boolean myOverflowToggle;
    private int myComparisonIndicator;

    private IODevice[] myDevices;

    public Simulator() {
        rA = new Register();
        rX = new Register();

        rI = new Register[6];
        for (int i = 0; i < 6; i++) {
            rI[i] = new IndexRegister();
        }

        rJ = new JumpRegister();

        myMemory = new Word[4000];
        for (int i = 0; i < 4000; i++) {
            myMemory[i] = new Word();
        }

        myOverflowToggle = false;
        myComparisonIndicator = 0;
    }

    public void run(Word inst) {
        int C = inst.getCommand();
        int F = inst.getField();
        int index = inst.getIndex();
        int offset = index == 0 ? 0 : getIndexRegister(index).getValue();
        int M = inst.getAddress() + offset;
        Word V = getV(M, F);

        // Arithmetic
        if (C == 1) {
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.add(rA, V));
        }
        if (C == 2) {
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.add(rA, Word.negate(V)));
        }
        if (C == 3) {
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.upperMultiply(rA, V));
            getRegisterX().setWord(Word.multiply(rA, V));
        }

        // Load
        if (C == 8) {
            load(getRegisterA(), M, F, false);
        }
        if (9 <= C && C <= 14) {
            load(getIndexRegister(C - 8), M, F, false);
        }
        if (C == 15) {
            load(getRegisterX(), M, F, false);
        }
        if (C == 16) {
            load(getRegisterA(), M, F, true);
        }
        if (17 <= C && C <= 22) {
            load(getIndexRegister(C - 16), M, F, true);
        }
        if (C == 23) {
            load(getRegisterX(), M, F, true);
        }
        // Store
        if (C == 24) {
            store(getRegisterA(), M, F);
        }
        if (25 <= C && C <= 30) {
            store(getIndexRegister(C - 24), M, F);
        }
        if (C == 31) {
            store(getRegisterX(), M, F);
        }
        if (C == 32) {
            store(getJumpRegister(), M, F);
        }
        if (C == 33) {
            setMemory(M, new Word());
        }
    }


    /**
     * Get the A register.
     * @return the register.
     */
    public Register getRegisterA() {
        return rA;
    }

    /**
     * Get the X register.
     * @return the register.
     */
    public Register getRegisterX() {
        return rX;
    }

    /**
     * Get the Ii register. Note that these registers are
     * indexed by 1.
     * @param i the register number.
     * @return the register.
     */
    public Register getIndexRegister(int i) {
        return rI[i - 1];
    }

    /**
     * Get the jump register.
     * @return the register.
     */
    public Register getJumpRegister() {
        return rJ;
    }

    /**
     * Get Word at memory address index.
     * @param index is the address.
     * @return the Word.
     */
    public Word getMemory(int index) {
        return new Word(myMemory[index]);
    }

    /**
     * Set Word at memory address index.
     * @param index is the address.
     * @param w is the word to be set.
     */
    public void setMemory(int index, Word w) {
        myMemory[index] = new Word(w);
    }


    public Word getV(int m, int field) {
        int left = field / 8;
        int right = field % 8;
        Word wordToLoad = getMemory(m);
        Word toReturn = new Word();
        if (left == 0) {
            toReturn.setSign(wordToLoad.getSign());
            left++;
        }

        // Set indices
        int i = 5;
        int j = right;

        while (j >= left) {
            toReturn.setByte(i, wordToLoad.getByte(j));
            j--;
            i--;
        }

        return toReturn;
    }


    /**
     * Loads a Word in memory into the Register
     * @param reg is the register.
     * @param m is the location to be loaded.
     * @param field is the field specification.
     */


    public void load(Register reg, int m, int field, boolean isNegative) {
        Word w = getV(m, field);

        if (isNegative) {
            w.setSign(!w.getSign());
        }

        reg.setWord(w);
    }

    /**
     * Stores a Word in the register into memory.
     * @param reg is the register.
     * @param m is the address where the Word will be stored.
     * @param field is the field specification.
     */
    public void store(Register reg, int m, int field) {
        int left = field / 8;
        int right = field % 8;

        if (left == 0) {
            myMemory[m].setSign(reg.getSign());
            left++;
        }

        int j = 5;
        for (int i = right; i >= left; i--) {
            myMemory[m].setByte(i, reg.getByte(j));
            j--;
        }
    }

    /**
     * Enters the value M into the register.
     * @param reg the register
     * @param m
     */
    public void enter(Register reg, boolean sign, int m, boolean isNegative) {

    }

    /**
     *
     * @param reg
     * @param m
     */
    public void increment(Register reg, int m) {

    }

    public void decrement(Register reg, int m) {

    }

    public void compare(Register reg, int m, int field) {

    }

    public void jump() {

    }

    public void shift(Register reg) {

    }

    public void io() {

    }

}
