public class Byte {
    private int a0;
    private int a1;
    private int a2;
    private int a3;
    private int a4;
    private int a5;

    public Byte() {
        a0 = 1;
        a1 = 0;
        a2 = 0;
        a3 = 0;
        a4 = 0;
        a5 = 0;
    }

    public Byte(String command, APart address, IPart index, FPart field) {
    	int addr = address.evaluate();
    	a0 = addr >= 0 ? 1 : -1;
    	addr = Math.abs(addr);
    	a1 = addr / 64;
    	a2 = addr % 64;
    	a3 = index.getValue();
    	a4 = field.getValue();
    	a5 = Assemble.convertToByte(command);
    }

    public Byte(int b0, int b1, int b2, int b3, int b4, int b5) {
        a0 = b0 / Math.abs(b0);
        a1 = b1;
        a2 = b2;
        a3 = b3;
        a4 = b4;
        a5 = b5;
    }

    public void execute() {

    }

    public void NOP() {
        return;
    }

    public void ADD(int address) {
        
    }

    public void FADD() {
        //TODO: finish
    }
}