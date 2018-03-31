package main;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public final static Map<String, Pair> COMMANDS = makeCommands();
	public final static Map<Character, Integer> CHARACTER_CODE = makeCharacterCode();
	public final static char[] CHARACTERS = {' ', 'A', 'B', 'C', 'D', 'E',
	'F', 'G', 'H', 'I', ' ', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', ' ', ' ', 'S', 'T', 'U', 'V', 'W',
	'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', '(', ')', '+', '-', '*', '/', '=', '$', '<', '>', '@', ';', ':', '\''};

	private static Map<String, Pair> makeCommands() {
        Map<String, Pair> commands = new HashMap<>();
		// No Operation
		commands.put("NOP", new Pair(0,0));
		// Arithmetic Operations
		commands.put("ADD", new Pair(1,5));
		commands.put("FADD", new Pair(1,6));
		commands.put("SUB", new Pair(2,5));
		commands.put("FSUB", new Pair(2,6));
		commands.put("MUL", new Pair(3,5));
		commands.put("FMUL", new Pair(3,6));
		commands.put("DIV", new Pair(4,5));
		commands.put("FDIV", new Pair(4,6));
		// Miscellaneous Operations
		commands.put("NUM", new Pair(5,0));
		commands.put("CHAR", new Pair(5,1));
		commands.put("HLT", new Pair(5,2));
		commands.put("SLA", new Pair(6,0));
		commands.put("SRA", new Pair(6,1));
		commands.put("SLAX", new Pair(6,2));
		commands.put("SRAX", new Pair(6,3));
		commands.put("SLC", new Pair(6,4));
		commands.put("SRC", new Pair(6,5));
		// Loader Operators
		commands.put("LDA", new Pair(8,5));
		commands.put("LD1", new Pair(9,5));
		commands.put("LD2", new Pair(10,5));
		commands.put("LD3", new Pair(11,5));
		commands.put("LD4", new Pair(12,5));
		commands.put("LD5", new Pair(13,5));
		commands.put("LD6", new Pair(14,5));
		commands.put("LDX", new Pair(15,5));
		commands.put("LDAN", new Pair(16,5));
		commands.put("LD1N", new Pair(17,5));
		commands.put("LD2N", new Pair(18,5));
		commands.put("LD3N", new Pair(19,5));
		commands.put("LD4N", new Pair(20,5));
		commands.put("LD5N", new Pair(21,5));
		commands.put("LD6N", new Pair(22,5));
		commands.put("LDXN", new Pair(23,5));
		// Store Operators
		commands.put("STA", new Pair(24,5));
		commands.put("ST1", new Pair(25,5));
		commands.put("ST2", new Pair(26,5));
		commands.put("ST3", new Pair(27,5));
		commands.put("ST4", new Pair(28,5));
		commands.put("ST5", new Pair(29,5));
		commands.put("ST6", new Pair(30,5));
		commands.put("STX", new Pair(31,5));
		commands.put("STJ", new Pair(32,2));
		commands.put("STZ", new Pair(33,5));
		// Input Output Operators
		commands.put("JBUS", new Pair(34,0));
		commands.put("IOC", new Pair(35,0));
		commands.put("IN", new Pair(36,0));
		commands.put("OUT", new Pair(37,0));
		commands.put("JRED", new Pair(38,0));
		// Jumps
		commands.put("JMP", new Pair(39,0));
		commands.put("JSJ", new Pair(39,1));
		commands.put("JOV", new Pair(39,2));
		commands.put("JNOV", new Pair(39,3));
		commands.put("JL", new Pair(39,4));
		commands.put("JE", new Pair(39,5));
		commands.put("JG", new Pair(39,6));
		commands.put("JGE", new Pair(39,7));
		commands.put("JNE", new Pair(39,8));
		commands.put("JLE", new Pair(39,9));
		
		commands.put("JAN", new Pair(40, 0));
		commands.put("JAZ", new Pair(40, 1));
		commands.put("JAP", new Pair(40, 2));
		commands.put("JANN", new Pair(40, 3));
		commands.put("JANZ", new Pair(40, 4));
		commands.put("JANP", new Pair(40, 5));
		
		commands.put("J1N", new Pair(41, 0));
		commands.put("J1Z", new Pair(41, 1));
		commands.put("J1P", new Pair(41, 2));
		commands.put("J1NN", new Pair(41, 3));
		commands.put("J1NZ", new Pair(41, 4));
		commands.put("J1NP", new Pair(41, 5));
		
		commands.put("J2N", new Pair(42, 0));
		commands.put("J2Z", new Pair(42, 1));
		commands.put("J2P", new Pair(42, 2));
		commands.put("J2NN", new Pair(42, 3));
		commands.put("J2NZ", new Pair(42, 4));
		commands.put("J2NP", new Pair(42, 5));
		
		commands.put("J3N", new Pair(43, 0));
		commands.put("J3Z", new Pair(43, 1));
		commands.put("J3P", new Pair(43, 2));
		commands.put("J3NN", new Pair(43, 3));
		commands.put("J3NZ", new Pair(43, 4));
		commands.put("J3NP", new Pair(43, 5));
		
		commands.put("J4N", new Pair(44, 0));
		commands.put("J4Z", new Pair(44, 1));
		commands.put("J4P", new Pair(44, 2));
		commands.put("J4NN", new Pair(44, 3));
		commands.put("J4NZ", new Pair(44, 4));
		commands.put("J4NP", new Pair(44, 5));
		
		commands.put("J5N", new Pair(45, 0));
		commands.put("J5Z", new Pair(45, 1));
		commands.put("J5P", new Pair(45, 2));
		commands.put("J5NN", new Pair(45, 3));
		commands.put("J5NZ", new Pair(45, 4));
		commands.put("J5NP", new Pair(45, 5));
		
		commands.put("J6N", new Pair(46, 0));
		commands.put("J6Z", new Pair(46, 1));
		commands.put("J6P", new Pair(46, 2));
		commands.put("J6NN", new Pair(46, 3));
		commands.put("J6NZ", new Pair(46, 4));
		commands.put("J6NP", new Pair(46, 5));
		
		commands.put("JXN", new Pair(47, 0));
		commands.put("JXZ", new Pair(47, 1));
		commands.put("JXP", new Pair(47, 2));
		commands.put("JXNN", new Pair(47, 3));
		commands.put("JXNZ", new Pair(47, 4));
		commands.put("JXNP", new Pair(47, 5));
		
		// Increase, Decrease, and Enter Operators
		commands.put("INCA", new Pair(48,0));
		commands.put("INC1", new Pair(49,0));
		commands.put("INC2", new Pair(50,0));
		commands.put("INC3", new Pair(51,0));
		commands.put("INC4", new Pair(52,0));
		commands.put("INC5", new Pair(53,0));
		commands.put("INC6", new Pair(54,0));
		commands.put("INCX", new Pair(55,0));
		
		commands.put("DECA", new Pair(48,1));
		commands.put("DEC1", new Pair(49,1));
		commands.put("DEC2", new Pair(50,1));
		commands.put("DEC3", new Pair(51,1));
		commands.put("DEC4", new Pair(52,1));
		commands.put("DEC5", new Pair(53,1));
		commands.put("DEC6", new Pair(54,1));
		commands.put("DECX", new Pair(55,1));
		
		commands.put("ENTA", new Pair(48,2));
		commands.put("ENT1", new Pair(49,2));
		commands.put("ENT2", new Pair(50,2));
		commands.put("ENT3", new Pair(51,2));
		commands.put("ENT4", new Pair(52,2));
		commands.put("ENT5", new Pair(53,2));
		commands.put("ENT6", new Pair(54,2));
		commands.put("ENTX", new Pair(55,2));
		
		commands.put("ENNA", new Pair(48,3));
		commands.put("ENN1", new Pair(49,3));
		commands.put("ENN2", new Pair(50,3));
		commands.put("ENN3", new Pair(51,3));
		commands.put("ENN4", new Pair(52,3));
		commands.put("ENN5", new Pair(53,3));
		commands.put("ENN6", new Pair(54,3));
		commands.put("ENNX", new Pair(55,3));
		
		// Comparison Operators
		commands.put("CMPA", new Pair(56, 5));
		commands.put("CMP1", new Pair(57, 5));
		commands.put("CMP2", new Pair(58, 5));
		commands.put("CMP3", new Pair(59, 5));
		commands.put("CMP4", new Pair(60, 5));
		commands.put("CMP5", new Pair(61, 5));
		commands.put("CMP6", new Pair(62, 5));
		commands.put("CMPX", new Pair(63, 5));

		return commands;
	}


	private static Map<Character, Integer> makeCharacterCode() {
	    Map<Character, Integer> characterCode = new HashMap<>();
        // Character Code
        characterCode.put(' ', 0);
        characterCode.put('A', 1);
        characterCode.put('B', 2);
        characterCode.put('C', 3);
        characterCode.put('D', 4);
        characterCode.put('E', 5);
        characterCode.put('F', 6);
        characterCode.put('G', 7);
        characterCode.put('H', 8);
        characterCode.put('I', 9);

        characterCode.put('J', 11);
        characterCode.put('K', 12);
        characterCode.put('L', 13);
        characterCode.put('M', 14);
        characterCode.put('N', 15);
        characterCode.put('O', 16);
        characterCode.put('P', 17);
        characterCode.put('Q', 18);
        characterCode.put('R', 19);


        characterCode.put('S', 22);
        characterCode.put('T', 23);
        characterCode.put('U', 24);
        characterCode.put('V', 25);
        characterCode.put('W', 26);
        characterCode.put('X', 27);
        characterCode.put('Y', 28);
        characterCode.put('Z', 29);
        characterCode.put('0', 30);
        characterCode.put('1', 31);
        characterCode.put('2', 32);
        characterCode.put('3', 33);
        characterCode.put('4', 34);
        characterCode.put('5', 35);
        characterCode.put('6', 36);
        characterCode.put('7', 37);
        characterCode.put('8', 38);
        characterCode.put('9', 39);
        characterCode.put('.', 40);
        characterCode.put(',', 41);
        characterCode.put('(', 42);
        characterCode.put(')', 43);
        characterCode.put('+', 44);
        characterCode.put('-', 45);
        characterCode.put('*', 46);
        characterCode.put('/', 47);
        characterCode.put('=', 48);
        characterCode.put('$', 49);
        characterCode.put('<', 50);
        characterCode.put('>', 51);
        characterCode.put('@', 52);
        characterCode.put(';', 53);
        characterCode.put(':', 54);
        characterCode.put('\'', 55);

        return characterCode;
    }
}
