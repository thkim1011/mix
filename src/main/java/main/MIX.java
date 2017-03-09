package main;

import java.io.IOException;
import assembly.Assemble;
import assembly.Instruction;
import register.Register;
import register.JumpRegister;


public class MIX {
	
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