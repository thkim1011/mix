package assembly;
import java.io.*;
import java.util.ArrayList;
import main.MIX;
import main.Word;
import assembly.symbol.DefinedSymbol;
import assembly.symbol.FutureReference;
import assembly.symbol.Symbol;

/**
 * Assemble.java
 * This class contains the assembler and all
 * necessary functions for the assembly process.
 * @author Tae Hyung Kim
 */

//TODO: Write down the program for the whole thing because it's getting confusing now. 
//TODO: Also come back to this program and make it better
public class Assemble {
	// Counter represents the memory position which the program is on
	public static int counter = 0;
	// Byte size is variable
	public static int byteSize = 64;
	// Defined Symbols
	public static ArrayList<DefinedSymbol> dsymbols = new ArrayList<DefinedSymbol>();
	// Local Symbols
	public static ArrayList<LocalSymbol> lsymbols = new ArrayList<LocalSymbol>();
	// Constants
	public static ArrayList<Word> constants = new ArrayList<Word>();

	/**
	 * Assembles the MIXAL program to MIX. In addition, it stores the program in
	 * <code>assembled</code> but this might have to change.
	 */
	public static void main(String[] args) throws IOException {
		// IO
		BufferedReader fin = new BufferedReader(new FileReader(args[0]));
		PrintWriter fout = new PrintWriter(
				new BufferedWriter(new FileWriter(args[0].substring(0, args[0].indexOf(".")) + ".mix")));

		// Main Loop
		while (true) {
			/*
			 * This section gets the input and partitions it into logical parts.
			 * and breaks when at the end of file and skips comments.
			 * Note that comments start with an asterisk.
			 */
			
			String in = fin.readLine();
			if (in == null) {
				break;
			}
			if (in.charAt(0) == '*') {
				continue;
			}
			in = in.toUpperCase();
			String[] linePartition = partitionLine(in);
			String[] addrPartition = partitionAddress(linePartition[2], linePartition[1]);
			
			/*
			 * This section adds symbols to the dsymbols list if found and is
			 * not a local symbol. Then local symbols will be added to the
			 * lsymbols list.
			 */
			findSymbols(linePartition);
			/*
			 * This section parses MIXAL specific lines, that is, lines
			 * containing ORIG, EQU, CON, ALF, and END. I currently have not
			 * added END yet.
			 */
			if (isMIXAL(linePartition)) {
				continue;
			}
			/*
			 * This section assembles MIX commands. It starts by differentiating between the APart of the MIXAL line. 
			 */
			assembleMIXAL(in, linePartition, addrPartition);
			counter++;
		}

		for (int i = 1; i < 4000; i++) {
			Instruction inst = MIX.prevMemory[i];
			if(inst != null) {
				MIX.memory[i] = new Word(inst.myCommand, inst.myAPart, inst.myIPart, inst.myFPart);
			}
			if (MIX.memory[i] == null) {
				fout.println("+ 00 00 00 00 00");
			} else {
				fout.println(MIX.memory[i]);
			}
		}
		fin.close();
		fout.close();
	}

	/**
	 * Finds the variable in the line if it exists and stores it.
	 * 
	 * @param linePartition
	 */
	private static void findSymbols(String[] linePartition) {
		// We need to make sure to skip when EQU is found because the symbol's
		// value will not necessarily be the location counter.
		if (linePartition[1].equals("EQU")) {
			return;
		}
		// Note that symbol names cannot be 2F or 2B since this causes danger
		if (linePartition[0].matches("[1-9](B|F)")) {
			throw new IllegalArgumentException("You may not use local variables dH or dF as a symbol name.");
		}
		if (!linePartition[0].equals("") && !(linePartition[0].length() == 2 && linePartition[0].matches("[1-9]H"))) {
			dsymbols.add(new DefinedSymbol(linePartition[0]));
		} else if (linePartition[0] != "") {
			lsymbols.add(new LocalSymbol(linePartition[0]));
		}
	}

	/**
	 * Checks if the line contains MIXAL specific commands, and parses it.
	 * 
	 * @param in
	 *            the line being examined
	 * @param linePartition
	 * 
	 * @param addrPartition
	 * @return
	 */
	private static boolean isMIXAL(String[] linePartition) {
		if (linePartition[1].equals("EQU")) {
			dsymbols.add(new DefinedSymbol(linePartition[0], new Expression(linePartition[2]).evaluate()));
			return true;
		}
		if (linePartition[1].equals("CON")) {
			MIX.memory[counter] = (new WValue(linePartition[2])).evaluate();
			counter++;
			return true;
		}
		if (linePartition[1].equals("ALF")) {
			// TODO: Implement ALF
			counter++;
			return true;
		}
		if (linePartition[1].equals("ORIG")) {
			counter = (new WValue(linePartition[2])).evaluate().getValue();
			return true;
		}
		return false;
	}

	private static void assembleMIXAL(String in, String[] linePartition, String[] addrPartition) {
		// Get Value of IPART
		IPart iPart = new IPart(addrPartition[1]);
		// Get Value of FPart
		FPart fPart;
		if (addrPartition[2].equals("")) {
			fPart = convertToField(linePartition[1]);
		} else {
			fPart = new FPart(addrPartition[2]);
		}
		
		// Interpret APart -- I should do bug testing for this code
		APart aPart;
		String addr = addrPartition[0];
		if(!addr.matches("[^\\+\\-\\*/:]*=") && addr.matches(".*[A-Z].*")) { //i.e. has no operations and is not literal
			// Check the validity of variable name
			if(!Symbol.isValidSymbolName(addr)) {
				throw new IllegalArgumentException("Symbol name is invalid.");
			}
			// If the variable is not defined
			boolean isDefined = false;
			for(DefinedSymbol a : dsymbols) {
				if (a.getName().equals(addr)) {
					isDefined = true;
				}
			}
			if(!isDefined) {
				aPart = new FutureReference(addr);
			}
			/*
			if(addr.charAt(0) == '=') {
				aPart = new LiteralConstant(addr);
			}
			*/
			else {
				aPart = new Expression(addr); // TODO: bad code... need to fix 
			}

		}
		else {
			aPart = new Expression(addr); // TODO: it seems to be that operators with empty string do not function well.
		}
		
		// Create Instruction and add to memory
		Instruction current = new Instruction(linePartition[1], aPart, iPart, fPart);
		MIX.prevMemory[counter] = current;
	}

	/**
	 * Returns an array consisting of three strings which are the three parts of
	 * a line of MIXAL code.
	 * 
	 * @param in
	 *            the line to be partitioned
	 * @return an array of three strings
	 */
	private static String[] partitionLine(String in) {
		StringBuilder line = new StringBuilder(in);
		String[] partition = new String[3];
		partition[0] = getFirstWord(line);
		partition[1] = getFirstWord(line);
		partition[2] = getFirstWord(line);
		return partition;
	}

	/**
	 * Returns an
	 * 
	 * @param address
	 *            String referring to the address portion of MIX instruction
	 * @param command
	 *            String referring to the command portion of MIX instruction
	 * @return A String[3] which contains the address split into its APart,
	 *         IPart, and FPart
	 */
	private static String[] partitionAddress(String address, String command) {
		String[] partition = new String[3];

		// Extract FPart
		int field = address.indexOf("(");
		if (field != -1) {
			partition[2] = address.substring(field);
			address = address.substring(0, field);
		} else {
			partition[2] = "";
		}

		// Extract IPart
		int index = address.indexOf(",");
		if (index != -1) {
			partition[1] = address.substring(index);
			address = address.substring(0, index);
		} else {
			partition[1] = "";
		}
		// Extract APart
		partition[0] = address;
		return partition;
	}

	/**
	 * getFirstWord method
	 * 
	 * @param line
	 *            A mutable string
	 * @return Returns the first string before encountering a space
	 */
	private static String getFirstWord(StringBuilder line) {
		int i = 0;
		int length = line.length();
		while (i < length && line.charAt(i) != ' ') {
			i++;
		}
		String first = line.substring(0, i);
		int j = i;
		while (j < length && line.charAt(j) == ' ') {
			j++;
		}
		line.delete(0, j);
		return first;
	}

	/**
	 * convertToByte
	 * 
	 * @param command
	 * @return
	 */

	// TODO: convert the following information to a table
	public static int convertToByte(String command) {
		switch (command.toUpperCase()) {
		case "NOP":
			return (0);
		case "ADD":
			return (1);
		case "SUB":
			return (2);
		case "MUL":
			return (3);
		case "DIV":
			return (4);
		case "NUM":
		case "CHAR":
		case "HLT":
			return (5);
		case "SLA":
		case "SRA":
		case "SLAX":
		case "SRAX":
		case "SLC":
		case "SRC":
			return (6);
		case "MOVE":
			return (7);
		case "LDA":
			return (8);
		case "LD1":
			return (9);
		case "LD2":
			return (10);
		case "LD3":
			return (11);
		case "LD4":
			return (12);
		case "LD5":
			return (13);
		case "LD6":
			return (14);
		case "LDX":
			return (15);
		case "LDAN":
			return (16);
		case "LD1N":
			return (17);
		case "LD2N":
			return (18);
		case "LD3N":
			return (19);
		case "LD4N":
			return (20);
		case "LD5N":
			return (21);
		case "LD6N":
			return (22);
		case "LDXN":
			return (23);
		case "STA":
			return (24);
		case "ST1":
			return (25);
		case "ST2":
			return (26);
		case "ST3":
			return (27);
		case "ST4":
			return (28);
		case "ST5":
			return (29);
		case "ST6":
			return (30);
		case "STX":
			return (31);
		case "STJ":
			return (32);
		case "STZ":
			return (33);
		case "JBUS":
			return (34);
		case "IOC":
			return (35);
		case "IN":
			return (36);
		case "OUT":
			return (37);
		case "JRED":
			return (38);
		case "JMP":
		case "JSJ":
		case "JOV":
		case "JNOV":
		case "JL":
		case "JE":
		case "JG":
		case "JGE":
		case "JNE":
		case "JLE":
			return (39);
		case "JAN":
		case "JAZ":
		case "JAP":
		case "JANN":
		case "JANZ":
		case "JANP":
			return (40);
		case "J1N":
		case "J1Z":
		case "J1P":
		case "J1NN":
		case "J1NZ":
		case "J1NP":
			return (41);
		case "J2N":
		case "J2Z":
		case "J2P":
		case "J2NN":
		case "J2NZ":
		case "J2NP":
			return (42);
		case "J3N":
		case "J3Z":
		case "J3P":
		case "J3NN":
		case "J3NZ":
		case "J3NP":
			return (43);
		case "J4N":
		case "J4Z":
		case "J4P":
		case "J4NN":
		case "J4NZ":
		case "J4NP":
			return (44);
		case "J5N":
		case "J5Z":
		case "J5P":
		case "J5NN":
		case "J5NZ":
		case "J5NP":
			return (45);
		case "J6N":
		case "J6Z":
		case "J6P":
		case "J6NN":
		case "J6NZ":
		case "J6NP":
			return (46);
		case "JXN":
		case "JXZ":
		case "JXP":
		case "JXNN":
		case "JXNZ":
		case "JXNP":
			return (47);
		case "INCA":
		case "DECA":
		case "ENTA":
		case "ENNA":
			return (48);
		case "INC1":
		case "DEC1":
		case "ENT1":
		case "ENN1":
			return (49);
		case "INC2":
		case "DEC2":
		case "ENT2":
		case "ENN2":
			return (50);
		case "INC3":
		case "DEC3":
		case "ENT3":
		case "ENN3":
			return (51);
		case "INC4":
		case "DEC4":
		case "ENT4":
		case "ENN4":
			return (52);
		case "INC5":
		case "DEC5":
		case "ENT5":
		case "ENN5":
			return (53);
		case "INC6":
		case "DEC6":
		case "ENT6":
		case "ENN6":
			return (54);
		case "INCX":
		case "DECX":
		case "ENTX":
		case "ENNX":
			return (55);
		case "CMPA":
		case "FCMP":
			return (56);
		case "CMP1":
			return (57);
		case "CMP2":
			return (58);
		case "CMP3":
			return (59);
		case "CMP4":
			return (60);
		case "CMP5":
			return (61);
		case "CMP6":
			return (62);
		case "CMPX":
			return (63);
		default:
			return (-1);
		}
	}

	public static FPart convertToField(String command) {
		switch (command.toUpperCase()) {
		case "NOP":
		case "NUM":
		case "SLA":
		case "JBUS":
		case "IOC":
		case "IN":
		case "OUT":
		case "JRED":
		case "JMP":
		case "JAN":
		case "J1N":
		case "J2N":
		case "J3N":
		case "J4N":
		case "J5N":
		case "J6N":
		case "JXN":
		case "INCA":
		case "IN1":
		case "INC2":
		case "INC3":
		case "INC4":
		case "INC5":
		case "INC6":
		case "INCX":
			return new FPart(0);
		case "CHAR":
		case "SRA":
		case "MOVE":
		case "JSJ":
		case "JAZ":
		case "J1Z":
		case "J2Z":
		case "J3Z":
		case "J4Z":
		case "J5Z":
		case "J6Z":
		case "JXZ":
		case "DECA":
		case "DEC1":
		case "DEC2":
		case "DEC3":
		case "DEC4":
		case "DEC5":
		case "DEC6":
		case "DECX":
			return new FPart(1);
		case "HLT":
		case "SLAX":
		case "STJ":
		case "JOV":
		case "JAP":
		case "J1P":
		case "J2P":
		case "J3P":
		case "J4P":
		case "J5P":
		case "J6P":
		case "JXP":
		case "ENTA":
		case "ENT1":
		case "ENT2":
		case "ENT3":
		case "ENT4":
		case "ENT5":
		case "ENT6":
		case "ENTX":
			return new FPart(2);
		case "SRAX":
		case "JNOV":
		case "JANN":
		case "J1NN":
		case "J2NN":
		case "J3NN":
		case "J4NN":
		case "J5NN":
		case "J6NN":
		case "JXNN":
		case "ENNA":
		case "ENN1":
		case "ENN2":
		case "ENN3":
		case "ENN4":
		case "ENN5":
		case "ENN6":
		case "ENNX":
			return new FPart(3);
		case "SLC":
		case "JL":
		case "JANZ":
		case "J1NZ":
		case "J2NZ":
		case "J3NZ":
		case "J4NZ":
		case "J5NZ":
		case "J6NZ":
		case "JXNZ":
			return new FPart(4);
		case "ADD":
		case "SUB":
		case "MUL":
		case "DIV":
		case "SRC":
		case "LDA":
		case "LD1":
		case "LD2":
		case "LD3":
		case "LD4":
		case "LD5":
		case "LD6":
		case "LDX":
		case "LDAN":
		case "LD1N":
		case "LD2N":
		case "LD3N":
		case "LD4N":
		case "LD5N":
		case "LD6N":
		case "LDXN":
		case "STA":
		case "ST1":
		case "ST2":
		case "ST3":
		case "ST4":
		case "ST5":
		case "ST6":
		case "STX":
		case "STZ":
		case "JE":
		case "JANP":
		case "J1NP":
		case "J2NP":
		case "J3NP":
		case "J4NP":
		case "J5NP":
		case "J6NP":
		case "JXNP":
		case "CMPA":
		case "CMP1":
		case "CMP2":
		case "CMP3":
		case "CMP4":
		case "CMP5":
		case "CMP6":
		case "CMPX":
			return new FPart(5);
		case "FADD":
		case "FSUB":
		case "FMUL":
		case "FDIV":
		case "FCMP":
		case "JG":
			return new FPart(6);
		case "JGE":
			return new FPart(7);
		case "JNE":
			return new FPart(8);
		case "JLE":
			return new FPart(9);
		default:
			return new FPart(-1);
		}
	}
}