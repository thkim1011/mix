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
    }

    public void run(Word inst) {

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

    public Word getMemory(int index) {
        return new Word(myMemory[index]);
    }

    public void setMemory(int index, Word w) {

    }

    public void load(Register reg, int m, int field) {

    }

    public void store(Register reg, int m, int field) {

    }
    /**
     * Applies arithmetic.
     * @param op is one of 1, 2, 3, 4, which corresponds to ADD, SUB, MUL, DIV, resp.
     * @param m
     * @param field
     */
    public void arithmetic(int op, int m, int field) {

    }

    /**
     * Enters the value M into the register.
     * @param reg the register
     * @param m
     */
    public void enter(Register reg, int m) {

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
