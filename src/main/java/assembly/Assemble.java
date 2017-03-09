package assembly;

import java.io.*;
import java.util.ArrayList;
import main.MIX;
import main.Word;
import assembly.symbol.DefinedSymbol;
import assembly.symbol.FutureReference;
import assembly.symbol.LocalSymbol;
import assembly.symbol.Symbol;

/**
 * <b>Assemble class.</b> This implementation of MIX consists of three
 * components: the assembler, the debugger, and the simulator. This class acts
 * to perform the operations of the assembler. In order to use this class, an
 * instance of an assembler must be created first using the constructor, which
 * contains a parameter for the file, and then the assemble method must be run.
 * 
 * @author Tae Hyung Kim
 */

public class Assemble {
	// Counter and Byte Size
	private int counter;
	private int byteSize;
	// Symbols
	private ArrayList<DefinedSymbol> dsymbols;
	private ArrayList<LocalSymbol> lsymbols;
	private ArrayList<Word> constants;
	// Instructions
	private ArrayList<String> program;
	private ArrayList<Instruction> assembled;
	
	/**
	 * The Assemble constructor creates an instance of the Assemble class which
	 * contains the methods to assemble a program written in MIXAL to MIX
	 * machine code.
	 * 
	 * 
	 * @param filename Name of the file which contains the MIX assembly program. 
	 * @throws IOException
	 */
	public Assemble(String filename) throws IOException {
		counter = 0;
		byteSize = 64;
		dsymbols = new ArrayList<DefinedSymbol>();
		lsymbols = new ArrayList<LocalSymbol>();
		constants = new ArrayList<Word>();
		program = new ArrayList<String>();
		
		// Instantiate Local Symbols
		for (int i = 0; i <= 9; i++) {
			lsymbols.add(new LocalSymbol());
		}
		
		// Save Program
		BufferedReader fin = new BufferedReader(new FileReader(filename));
		while(true) {
			String line = fin.readLine();
			if(line == null) {
				break;
			}
		}
	}

	public void assemble(String filename) throws IOException {
		PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		
	}
	
	
	
	public void main(String[] args) {
		

		// Main Loop
		while (true) {
			/*
			 * This section gets the input and partitions it into logical parts.
			 * and breaks when at the end of file and skips comments. Note that
			 * comments start with an asterisk.
			 */

			String in = fin.readLine();
			if (in == null) {
				break;
			}
			if (in.charAt(0) == '*') {
				// Adding Debugging functions stuff...
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
			 * This section assembles MIX commands. It starts by differentiating
			 * between the APart of the MIXAL line.
			 */
			assembleMIXAL(in, linePartition, addrPartition);
			counter++;
		}

		for (int i = 1; i < 4000; i++) {
			Instruction inst = MIX.prevMemory[i];
			if (inst != null) {
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
	private void findSymbols(String[] linePartition) {
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
	private boolean isMIXAL(String[] linePartition) {
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
		if (!addr.matches("[^\\+\\-\\*/:]*=") && addr.matches(".*[A-Z].*")) { // i.e.
																				// has
																				// no
																				// operations
																				// and
																				// is
																				// not
																				// literal
			// Check the validity of variable name
			if (!Symbol.isValidSymbolName(addr)) {
				throw new IllegalArgumentException("Symbol name is invalid.");
			}
			// If the variable is not defined
			boolean isDefined = false;
			for (DefinedSymbol a : dsymbols) {
				if (a.getName().equals(addr)) {
					isDefined = true;
				}
			}
			if (!isDefined) {
				aPart = new FutureReference(addr);
			}
			/*
			 * if(addr.charAt(0) == '=') { aPart = new LiteralConstant(addr); }
			 */
			else {
				aPart = new Expression(addr); // TODO: bad code... need to fix
			}

		} else {
			aPart = new Expression(addr); // TODO: it seems to be that operators
											// with empty string do not function
											// well.
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
}