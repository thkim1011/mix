public class Instruction extends Word {
    private int myCounter;

    public Instruction(String command, APart address, IPart index, FPart field) {
        int a = address.evaluate();
        int sign = Math.abs(a)/a;
        int a = Math.abs(a);
        super(sign, a/64, a%64, index, field, Assemble.convertToByte(command));
        node = null;
        myCounter = -1;
    }

    public Instruction(String command, APart address, IPart index, FPart field, int counter) {
        int a = address.evaluate();
        int sign = Math.abs(a)/a;
        int a = Math.abs(a);
        super(sign, a/64, a%64, index, field, Assemble.convertToByte(command));
        node = null;
        myCounter = counter;
    }

    public void execute(Word[] memory, Register[] registers, Boolean isOverflow, Integer comparison) {
        
    }

    private void setNext(Instruction next) {
        node = next;
    }

    public int getCounter() {
        return myCounter;
    }
}