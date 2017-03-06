package main;

import java.io.IOException;
import assembly.Assemble;
import assembly.Instruction;
import register.Register;
import register.JumpRegister;

public class MIX {
	final public static Register rA = new Register(5);
	final public static Register rX = new Register(5);
	//TODO: Every rI[i] are not final.
	final public static Register[] rI = { new Register(2), // DUMMY REGISTER
			new Register(2), 
			new Register(2), 
			new Register(2), 
			new Register(2),
			new Register(2),
			new Register(2)};
	final public static Register rJ = new JumpRegister();
	final public static Instruction[] prevMemory = new Instruction[4000]; // TODO: NOTE THAT THIS IS PRETTY BAD CODE... FIX LATER
	final public static Word[] memory = new Word[4000];
	public static boolean overflowToggle = false;
	public static int comparisonIndicator = 0;
	public static int currentInst = 0;
	public static boolean isHalted = false;

	public static void main(String[] args) throws IOException {
		Assemble.main(args);

		while (true) {
			// TODO: Find out HOW a MIX computer actually stops itself.
			// Meanwhile, I'm just going to use the HALT.
			if (isHalted) {
				break;
			}
			memory[currentInst].execute();
			currentInst++;
		}
	}
}