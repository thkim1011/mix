public class Instruction extends Word {
    private int myCounter;

    public Instruction(String command, APart address, IPart index, FPart field) {
        super(address.evaluate(), new Byte(Math.abs(address.evaluate())/64), new Byte(Math.abs(address.evaluate())%64), index, field, Assemble.convertToByte(command));
        myCounter = -1;
    }
    public Instruction(String command, APart address, IPart index, FPart field, int counter) {
        super(address.evaluate(), new Byte(Math.abs(address.evaluate())/64), new Byte(Math.abs(address.evaluate())%64), index, field, Assemble.convertToByte(command));
        myCounter = counter;
    }


    public int getCounter() {
        return myCounter;
    }
}