package simulator;

import simulator.register.JumpRegister;
import simulator.register.Register;
import main.Word;

/**
 * <b>Simulate class.</b> This implementation of MIX consists of three
 * components: the assembler, the debugger, and the simulator. This class acts
 * to perform the operations of the simulator. The simulator will follow the
 * conventions and mechanism of the MIX computer described by Donald Knuth in
 * <i>The Art of Computer Programming</i>.
 * 
 * The structure of the class has some points that should be explained. The
 * first nine instance variables consist of the registers. Each are of the type
 * Register, and each are a Register object, except for the jump simulator.register, which
 * uses a subclass called JumpRegister.
 * 
 * The next four instance variables consist of the memory, the overflow toggle,
 * the comparison indicator, and the current instruction. The memory is an array
 * of 4000 words. The overflow toggle is a boolean where false indicates that
 * there is no overflow and true indicates that there is an overflow. The
 * comparison indicator gives 1 for G, 0 for E, and -1 for L. The current
 * instruction gives the current instruction of the machine.
 * 
 * @author Tae Hyung Kim
 *
 */
public class Simulate {
	// Defining All Registers
	final private Register myRegA;
	final private Register myRegX;
	final private Register myReg1;
	final private Register myReg2;
	final private Register myReg3;
	final private Register myReg4;
	final private Register myReg5;
	final private Register myReg6;
	final private Register myRegJ;
	// Memory of Length 4000
	final private Word[] myMemory;
	// Toggles and Indicators
	private boolean myOverflowToggle;
	private int myComparisonIndicator;
	// Current Instruction
	private int myCurrentInst;

	// Default Constructor
	public Simulate() {
		// Assign Registers
		myRegA = new Register(5);
		myRegX = new Register(5);
		myReg1 = new Register(2);
		myReg2 = new Register(2);
		myReg3 = new Register(2);
		myReg4 = new Register(2);
		myReg5 = new Register(2);
		myReg6 = new Register(2);
		myRegJ = new JumpRegister();
		// Assign Memory
		myMemory = new Word[4000];
		// Toggles and Indicators
		myOverflowToggle = false;
		myComparisonIndicator = 0;
		// Current Instruction
		myCurrentInst = 0;
	}
	
	public void setRegister(String name, Word value) {
		if(name.equals("A")) {
			myRegA.setRegister(value);
		}
		else if(name.equals("X")) {
			myRegX.setRegister(value);
		}
		else if(name.equals("I1")) {
			myReg1.setRegister(value);
		}
		else if(name.equals("I2")) {
			myReg2.setRegister(value);
		}
		else if(name.equals("I3")) {
			myReg3.setRegister(value);
		}
		else if(name.equals("I4")) {
			myReg4.setRegister(value);
		}
		else if(name.equals("I5")) {
			myReg5.setRegister(value);
		}
		else if(name.equals("I6")) {
			myReg6.setRegister(value);
		}
		else {
			throw new IllegalArgumentException("Your simulator.register name is invalid."); //TODO: Try to create a standard error throwing convention...
		}
	}
}
