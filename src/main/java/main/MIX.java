package main;

import assembler.Assemble;
import validator.Validate;

import java.io.IOException;


public class MIX {
	
	public static boolean overflowToggle = false;
	public static int comparisonIndicator = 0;
	public static int currentInst = 0;
	public static boolean isHalted = false;

	public static void main(String[] args) throws IOException {
		Validate validator = new Validate(args[0]);
	    Assemble assembler = new Assemble(args[0], args[0].substring(0, args[0].indexOf(".mixal")) + ".mix");
	}
}