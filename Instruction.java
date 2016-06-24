public class Instruction extends Word {
    private Instruction node;

    public Instruction(String command, APart address, IPart index, FPart field) {
        int a = address.evaluate();
        int sign = Math.abs(a)/a;
        int a = Math.abs(a);
        super(sign, a/64, a%64, index, field, Assemble.convertToByte(command));
        node = null;
    }

    public void execute(Word[] memory, Register[] registers, Boolean isOverflow, Integer comparison) {
        
    }


    private Byte getNormalField(String command) {
        
    }

    private Instruction getNext() {
        return node;
    }

    private void setNext(Instruction next) {
        node = next;
    }
}