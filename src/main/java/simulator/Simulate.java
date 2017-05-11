package simulator;

import assembler.Assemble;
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

	public void run(Assemble assembler) {

		// Get Program
        for(int i = 0; i < 4000; i++) {
            myMemory[i] = assembler.getAssembled(i);
        }

        // Construct Operators
        Loader loader = new Loader();
        Store storer = new Store();
		IO io = new IO();

        // Get Current Instruction
        myCurrentInst = assembler.getStart();

        while (true) {
            Word inst = myMemory[myCurrentInst];
			myCurrentInst++;
            // Construct Address
            int address;

            if (inst.getByte(3).getValue() == 0) {
				address = (inst.getSign() ? 1 : -1 )
						* (inst.getByte(1).getValue() * 64
						+ inst.getByte(2).getValue());
			}
			else {
            	address = (inst.getSign() ? 1 : -1 )
						* (inst.getByte(1).getValue()
						+ inst.getByte(2).getValue())
						+ getRegister("I" + inst.getByte(3)).getValue();
			}


            // Get Field
            int field = inst.getByte(4).getValue();

            // Get Command
            int command = inst.getByte(5).getValue();

            // Casework on Command
            if (command == 0) { // NOP
                continue;
            }
            else if (1 <= command && command <= 4) { // ADD, SUB, MUL, DIV

            }
            else if (command == 5) { // NUM, CHAR, HLT
				if (field == 2) {
					break;
				}
            }
            else if (command == 6) { // SLA, SRA, SLAX, SRAX, SLC, SRC
				storer.store(address, field, command, this);
            }
            else if (command == 7) { // MOVE
            }
            else if (8 <= command && command <= 23) { // LOAD
                loader.load(address, field, command, this);
            }
            else if (24 <= command && command <= 33) { // STORE
				
            }
            else if (34 <= command && command <= 38) { // IO
				if (command == 37) {
					io.out(address, field, this);
				}
            }
            else if (39 <= command && command <= 47) { // JUMP

            }
            else if (48 <= command && command <= 55) { // ENTER

            }
            else if (56 <= command && command <= 63) { // COMPARE

            }
            else {
                throw new IllegalArgumentException("Command nonexistent");
            }
        }

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

	public Word getRegister(String name) {
	    if (name.equals("A")) {
	        return myRegA.getWord();
        }
        else if (name.equals("X")) {
	        return myRegX.getWord();
        }
        else if(name.equals("I1")) {
	        return myReg1.getWord();
        }
        else if(name.equals("I2")) {
	        return myReg2.getWord();
        }
        else if(name.equals("I3")) {
	        return myReg3.getWord();
        }
        else if(name.equals("I4")) {
            return myReg3.getWord();
        }
        else if(name.equals("I5")) {
            return myReg3.getWord();
        }
        else if(name.equals("I6")) {
            return myReg3.getWord();
        }
        else {
	        throw new IllegalArgumentException("Your register name is inalid");
        }
	}

	public Word getWord(int index) {
	    return myMemory[index];
    }

}
