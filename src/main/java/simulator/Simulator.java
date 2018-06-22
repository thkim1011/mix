package simulator;

import main.Word;
import simulator.io.*;

public class Simulator {

    private Register rA;
    private Register rX;
    private Register[] rI;
    private Register rJ;

    private Word[] myMemory;

    private boolean myOverflowToggle;
    private int myComparisonIndicator;
    private int myCounter;
    private boolean isHalted;

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
        myCounter = 0;
        isHalted = false;

        myDevices = new IODevice[21];

        for (int i = 0; i <= 7; i++) {
            myDevices[i] = new TapeUnit(i);
        }
        for (int i = 8; i <= 15; i++) {
            myDevices[i] = new DiskUnit(i);
        }
        myDevices[16] = new CardReader();
        myDevices[17] = new CardWriter();
        myDevices[18] = new LinePrinter();
        myDevices[19] = new TypeWriter();
        myDevices[20] = new PaperTape();
    }

    public Simulator(Word[] program, int initial) {
        this();
        for (int i = 0; i < 4000; i++) {
            myMemory[i] = new Word(program[i]);
        }
        myCounter = initial;
    }

    public void run(Word inst) {
        int C = inst.getCommand();
        int F = inst.getField();
        int index = inst.getIndex();
        int offset = index == 0 ? 0 : getIndexRegister(index).getValue();
        int M = inst.getAddress() + offset;
        myCounter++;

        // Arithmetic
        if (C == 1) {
            Word V = getV(M, F);
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.add(rA, V));
        }
        if (C == 2) {
            Word V = getV(M, F);
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.add(rA, Word.negate(V)));
        }
        if (C == 3) {
            Word V = getV(M, F);
            Word rA = getRegisterA().getWord();
            getRegisterA().setWord(Word.upperMultiply(rA, V));
            getRegisterX().setWord(Word.multiply(rA, V));
        }

        if (C == 4) {
            Word V = getV(M, F);
            Word rA = getRegisterA().getWord();
            Word rX = getRegisterX().getWord();
            int val = rA.getValue() * 64 * 64 * 64 * 64 * 64 + rX.getValue();
            int quotient = val / V.getValue();
            int remainder = val % V.getValue();
            getRegisterA().setWord(new Word(quotient));
            getRegisterX().setWord(new Word(remainder));
        }

        // Special
        if (C == 5) {
            if (F == 0) {
                Word wA = getRegisterA().getWord();
                Word wX = getRegisterX().getWord();
                int val = 0;
                for (int i = 1; i <= 5; i++) {
                    val = val * 10 + wA.getByte(i) % 10;
                }
                for (int i = 1; i <= 5; i++) {
                    val = val * 10 + wX.getByte(i) % 10;
                }
                boolean sign = getRegisterA().getSign();
                getRegisterA().setWord(new Word(val));
                getRegisterA().setSign(sign);
            }
            if (F == 1) {
                Integer number = Math.abs(getRegisterA().getValue());
                String strNum = number.toString();
                strNum = "0000000000" + strNum;
                int i = strNum.length() - 1;
                for (int j = 5; j > 0; j--) {
                    getRegisterX().setByte(j, 30 + strNum.charAt(i) - '0');
                    i--;
                }
                for (int j = 5; j > 0; j--) {
                    getRegisterA().setByte(j, 30 + strNum.charAt(i) - '0');
                    i--;
                }
            }
            if (F == 2) {
                isHalted = true;
            }
        }
        // Shift
        if (C == 6) {
            shift(M, F);
        }

        // Move
        if (C == 7) {
            int j = getIndexRegister(1).getValue();
            for (int i = M; i <= M + F; i++) {
                setMemory(j, getMemory(i));
                j++;
            }
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

        // IO
        // TODO: Implement all IO Devices
        if (C == 35) {
            myDevices[F].control(M);
        }
        if (C == 36) {
            for (int i = M; i < M + myDevices[F].getBlockSize(); i++) {
                setMemory(i, ((InputDevice) myDevices[F]).getWord());
            }
        }
        if (C == 37) {
            for (int i = M; i < M + myDevices[F].getBlockSize(); i++) {
                ((OutputDevice) myDevices[F]).printWord(getMemory(i));
            }
            System.out.println("");
        }

        // Jump
        if (C == 39) {
            if (F == 0 ||
                    (F == 2 && myOverflowToggle) ||
                    (F == 3 && !myOverflowToggle) ||
                    (F == 4 && myComparisonIndicator == -1) ||
                    (F == 5 && myComparisonIndicator == 0) ||
                    (F == 6 && myComparisonIndicator == 1) ||
                    (F == 7 && !(myComparisonIndicator == -1)) ||
                    (F == 8 && !(myComparisonIndicator == 0)) ||
                    (F == 9 && !(myComparisonIndicator == 1))) {
                getJumpRegister().setWord(new Word(myCounter));
                myCounter = M;
            }
            if (F == 1) {
                myCounter = M;
            }
        }

        if (C == 40) {
            jump(getRegisterA(), M, F);
        }

        if (41 <= C && C <= 46) {
            jump(getIndexRegister(C - 40), M, F);
        }

        if (C == 47) {
            jump(getRegisterX(), M, F);
        }

        if (C == 48) {
            if (F == 0) {
                increment(getRegisterA(), M);
            }
            if (F == 1) {
                decrement(getRegisterA(), M);
            }
            if (F == 2) {
                enter(getRegisterA(), M, false);
            }
            if (F == 3) {
                enter(getRegisterA(), M, true);
            }
        }
        if (49 <= C && C <= 54) {
            if (F == 0) {
                increment(getIndexRegister(C - 48), M);
            }
            if (F == 1) {
                decrement(getIndexRegister(C - 48), M);
            }
            if (F == 2) {
                enter(getIndexRegister(C - 48), M, false);
            }
            if (F == 3) {
                enter(getIndexRegister(C - 48), M, true);
            }
        }
        if (C == 55) {
            if (F == 0) {
                increment(getRegisterX(), M);
            }
            if (F == 1) {
                decrement(getRegisterX(), M);
            }
            if (F == 2) {
                enter(getRegisterX(), M, false);
            }
            if (F == 3) {
                enter(getRegisterX(), M, true);
            }
        }

        // Compare
        if (C == 56) {
            compare(getRegisterA(), M, F);
        }
        if (57 <= C && C <= 62) {
            compare(getIndexRegister(C - 56), M, F);
        }
        if (C == 63) {
            compare(getRegisterX(), M, F);
        }
    }

    public void simulate() {
        while(!isHalted) {
            run(myMemory[myCounter]);
        }
    }

    public void closeDevices() {
        for (int i = 0; i < 21; i++) {
            myDevices[i].close();
        }
    }

    public void simulateNextWord() {
        run(myMemory[myCounter]);
    }

    /**
     * Get the A register.
     *
     * @return the register.
     */
    public Register getRegisterA() {
        return rA;
    }

    /**
     * Get the X register.
     *
     * @return the register.
     */
    public Register getRegisterX() {
        return rX;
    }

    /**
     * Get the Ii register. Note that these registers are
     * indexed by 1.
     *
     * @param i the register number.
     * @return the register.
     */
    public Register getIndexRegister(int i) {
        return rI[i - 1];
    }

    /**
     * Get the jump register.
     *
     * @return the register.
     */
    public Register getJumpRegister() {
        return rJ;
    }

    /**
     * Get Word at memory address index.
     *
     * @param index is the address.
     * @return the Word.
     */
    public Word getMemory(int index) {
        return new Word(myMemory[index]);
    }

    /**
     * Set Word at memory address index.
     *
     * @param index is the address.
     * @param w     is the word to be set.
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
     *
     * @param reg   is the register.
     * @param m     is the location to be loaded.
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
     *
     * @param reg   is the register.
     * @param m     is the address where the Word will be stored.
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
     *
     * @param reg the register
     * @param m
     */
    public void enter(Register reg, int m, boolean isNegative) {
        Word w = new Word(m);
        if (isNegative) {
            w.setSign(!w.getSign());
        }
        reg.setWord(w);
    }

    /**
     * @param reg
     * @param m
     */
    public void increment(Register reg, int m) {
        Word w = new Word(m);
        reg.setWord(Word.add(reg.getWord(), w));
    }

    public void decrement(Register reg, int m) {
        Word w = new Word(m);
        reg.setWord(Word.add(reg.getWord(), Word.negate(w)));
    }

    public void compare(Register reg, int m, int field) {
        int regVal = reg.getWord().getValueByField(field);
        int contents = getMemory(m).getValueByField(field);
        if (regVal > contents) {
            myComparisonIndicator = 1;
        }
        if (regVal == contents) {
            myComparisonIndicator = 0;
        }
        if (regVal < contents) {
            myComparisonIndicator = -1;
        }
    }

    public void jump(Register reg, int m, int field) {
        if ((field == 0 && reg.getValue() < 0) ||
                (field == 1 && reg.getValue() == 0) ||
                (field == 2 && reg.getValue() > 0) ||
                (field == 3 && reg.getValue() >= 0) ||
                (field == 4 && reg.getValue() != 0) ||
                (field == 5 && reg.getValue() <= 0)) {
            getJumpRegister().setWord(new Word(myCounter));
            myCounter = m;
        }
    }

    public void shift(int m, int field) {
        if (field == 0)  { // SLA
            Word w = new Word();
            w.setSign(getRegisterA().getSign());
            for (int i = m + 1; i <= 5; i++) {
                w.setByte(i - m, getRegisterA().getByte(i));
            }
            getRegisterA().setWord(w);
        }
        else if (field == 1) { // SRA
            Word w = new Word();
            w.setSign(getRegisterA().getSign());
            for (int i = 5 - m; i >= 1; i--) {
                w.setByte(i + m, getRegisterA().getByte(i));
            }
            getRegisterA().setWord(w);
        }
        else if (field == 2) { // SLAX
            Word w1 = new Word();
            Word w2 = new Word();
            w1.setSign(getRegisterA().getSign());
            w2.setSign(getRegisterX().getSign());
            for (int i = m + 1; i <= 10; i--) {
                setCombinedByte(w1, w2, i - m, getCombinedByte(i));
            }
            getRegisterA().setWord(w1);
            getRegisterX().setWord(w2);
        }
        else if (field == 3) { // SRAX
            Word w1 = new Word();
            Word w2 = new Word();
            w1.setSign(getRegisterA().getSign());
            w2.setSign(getRegisterX().getSign());
            for (int i = 10 - m; i >= 1; i--) {
                setCombinedByte(w1, w2, i + m, getCombinedByte(i));
            }
            getRegisterA().setWord(w1);
            getRegisterX().setWord(w2);
        }
        else if (field == 4) { // SLC
            Word w1 = new Word();
            Word w2 = new Word();
            w1.setSign(getRegisterA().getSign());
            w2.setSign(getRegisterX().getSign());
            for (int i = 1; i <= 10; i++) {
                setCombinedByte(w1, w2, Math.floorMod(i - m - 1, 10) + 1, getCombinedByte(i));
            }
            getRegisterA().setWord(w1);
            getRegisterX().setWord(w2);
        }
        else if (field == 5) {
            Word w1 = new Word();
            Word w2 = new Word();
            w1.setSign(getRegisterA().getSign());
            w2.setSign(getRegisterX().getSign());
            for (int i = 1; i <= 10; i++) {
                setCombinedByte(w1, w2, Math.floorMod(i + m - 1, 10) + 1, getCombinedByte(i));
            }
            getRegisterA().setWord(w1);
            getRegisterX().setWord(w2);
        }
    }

    public int getCombinedByte(int pos) {
        if (1 <= pos && pos <= 5) {
            return getRegisterA().getByte(pos);
        }
        else if (6 <= pos && pos <= 10) {
            return getRegisterX().getByte(pos - 5);
        }
        else {
            throw new IllegalArgumentException("Byte position out of bounds");
        }
    }

    public void setCombinedByte(Word w1, Word w2, int pos, int val) {
        if (1 <= pos && pos <= 5) {
            w1.setByte(pos, val);
        }
        else if (6 <= pos && pos <= 10) {
            w2.setByte(pos - 5, val);
        }
        else {
            throw new IllegalArgumentException("Byte position out of bounds");
        }
    }


    public void io() {

    }

    public int getCounter() {
        return myCounter;
    }
}
