import java.io.*;
import java.util.*;

public class Assemble {
    public static int counter = 0;

    public static ArrayList<DefinedSymbol> symbols = new ArrayList<DefinedSymbol>();

    public static void main(String[] args) throws IOException{
        // IO
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(args[0].substring(0,args[0].indexOf(".")) + ".machine")));
        
        // Create and Partition File
        Instruction first;
        Instruction inst;

        while(true) {
            StringBuilder line;
            String in = fin.readLine();
            
            // Break when end of file
            if(in == null) {
                break;
            }

            // Otherwise set line to in
            line = new StringBuilder(in);

            if(line.charAt(0) != '*') { // Skip comments
                String[] partition = partitionLine(line);
                Object[] linePartition = partitionAddress(partition[2]);
                thisLine = new Instruction(partition[1], )
                if(inst == null) {
                    inst = new Instruction(partition[1], linePartition[0], linePartition[1], linePartition[2]);
                    first = inst;
                }
                else {
                    first.setNext() = new Instruction(partition[1], linePartition[0], linePartition[1], linePartition[2]);
                }
            }
        }

        // Evaluate and Replace all expressions
        inst = first; 
        
    }

    public static String[] partitionLine(StringBuilder line) {
        String address;
        String[] partition = new String[3];
        
        partition[0] = getFirstWord(line);
        partition[1] = getFirstWord(line);
        partition[2] = getFirstWord(line);

        return partition;
    }

    public static Object[] partitionAddress(String address, String command) {
        Object[] partition = new Object[3];
        APart a;
        IPart i;
        FPart f;

        // Extract FPart
        int field = address.indexOf("(");
        if (field != -1) {
            partition[2] = new FPart(address.substring(field));
            address = address.substring(0, field);
        }
        else {
            partition[2] = convertToField(command);
        }
        
        // Extract IPart
        int index = address.indexOf(",");
        if (index != - 1) {
            partition[1] = new IPart(address.substring(index));
            address = address.substring(0, index);
        }
        else {
            partition[1] = new IPart();
        }
        // Extract APart
        partition[0] = APart(address);
    }

    public static String getFirstWord(StringBuilder line) {
        int i = 0;
        int length = line.length();
        while(i < length && line.charAt(i) != ' ') {
            i++;
        }
        String first = line.substring(0,i);
        int j = i;
        while(j < length && line.charAt(j) == ' ') {
            j++;
        }
        line.delete(0, j);
        return first;
    }

    public static Byte convertToByte(String command) {
        // TODO: convert to upper case....
        switch(command.toUpperCase()) {
            case "NOP": return new Byte(0);
            case "ADD": return new Byte(1);
            case "SUB": return new Byte(2);
            case "MUL": return new Byte(3);
            case "DIV": return new Byte(4);
            case "NUM": case "CHAR": case "HLT": return new Byte(5);
            case "SLA": case "SRA": case "SLAX": case "SRAX": case "SLC": case "SRC": return new Byte(6); 
            case "MOVE": return new Byte(7);
            case "LDA": return new Byte(8);
            case "LD1": return new Byte(9);
            case "LD2": return new Byte(10);
            case "LD3": return new Byte(11);
            case "LD4": return new Byte(12);
            case "LD5": return new Byte(13);
            case "LD6": return new Byte(14);
            case "LDX": return new Byte(15);
            case "LDAN": return new Byte(16);
            case "LD1N": return new Byte(17);
            case "LD2N": return new Byte(18);
            case "LD3N": return new Byte(19);
            case "LD4N": return new Byte(20);
            case "LD5N": return new Byte(21);
            case "LD6N": return new Byte(22);
            case "LDXN": return new Byte(23);
            case "STA": return new Byte(24);
            case "ST1": return new Byte(25);
            case "ST2": return new Byte(26);
            case "ST3": return new Byte(27);
            case "ST4": return new Byte(28);
            case "ST5": return new Byte(29);
            case "ST6": return new Byte(30);
            case "STX": return new Byte(31);
            case "STJ": return new Byte(32);
            case "STZ": return new Byte(33);
            case "JBUS": return new Byte(34);
            case "IOC": return new Byte(35);
            case "IN": return new Byte(36);
            case "OUT": return new Byte(37);
            case "JRED": return new Byte(38);
            case "JMP": case "JSJ": case "JOV": case "JNOV": case "JL": case "JE": case "JG": case "JGE": case "JNE": case "JLE": return new Byte(39);
            case "JAN": case "JAZ": case "JAP": case "JANN": case "JANZ": case "JANP": return new Byte(40);
            case "J1N": case "J1Z": case "J1P": case "J1NN": case "J1NZ": case "J1NP": return new Byte(41);
            case "J2N": case "J2Z": case "J2P": case "J2NN": case "J2NZ": case "J2NP": return new Byte(42);
            case "J3N": case "J3Z": case "J3P": case "J3NN": case "J3NZ": case "J3NP": return new Byte(43);
            case "J4N": case "J4Z": case "J4P": case "J4NN": case "J4NZ": case "J4NP": return new Byte(44);
            case "J5N": case "J5Z": case "J5P": case "J5NN": case "J5NZ": case "J5NP": return new Byte(45);
            case "J6N": case "J6Z": case "J6P": case "J6NN": case "J6NZ": case "J6NP": return new Byte(46);
            case "JXN": case "JXZ": case "JXP": case "JXNN": case "JXNZ": case "JXNP": return new Byte(47);
            case "INCA": case "DECA": case "ENTA": case "ENNA": return new Byte(48);
            case "INC1": case "DEC1": case "ENT1": case "ENN1": return new Byte(49);
            case "INC2": case "DEC2": case "ENT2": case "ENN2": return new Byte(50);
            case "INC3": case "DEC3": case "ENT3": case "ENN3": return new Byte(51);
            case "INC4": case "DEC4": case "ENT4": case "ENN4": return new Byte(52);
            case "INC5": case "DEC5": case "ENT5": case "ENN5": return new Byte(53);
            case "INC6": case "DEC6": case "ENT6": case "ENN6": return new Byte(54);
            case "INCX": case "DECX": case "ENTX": case "ENNX": return new Byte(55);
            case "CMPA": case "FCMP": return new Byte(56);
            case "CMP1": return new Byte(57);
            case "CMP2": return new Byte(58);
            case "CMP3": return new Byte(59);
            case "CMP4": return new Byte(60);
            case "CMP5": return new Byte(61);
            case "CMP6": return new Byte(62);
            case "CMPX": return new Byte(63);
            default: return new Byte(-1);
        }
    }

    public static FPart convertToField(String command) {
        switch(command.toUpperCase()) {
            case "NOP": case "NUM": case "SLA": case "JBUS": case "IOC": case "IN": case "OUT": case "JRED": case "JMP": case "JAN": case "J1N": case "J2N": case "J3N": case "J4N": case "J5N": case "J6N": case "JXN": case "INCA": case "IN1": case "INC2": case "INC3": case "INC4": case "INC5": case "INC6": case "INCX": return new FPart(0);
            case "CHAR": case "SRA": case "MOVE": case "JSJ": case "JAZ": case "J1Z": case "J2Z": case "J3Z": case "J4Z": case "J5Z": case "J6Z": case "JXZ": case "DECA": case "DEC1": case "DEC2": case "DEC3": case "DEC4": case "DEC5": case "DEC6": case "DECX": return new FPart(1);
            case "HLT": case "SLAX": case "STJ": case "JOV": case "JAP": case "J1P": case "J2P": case "J3P": case "J4P": case "J5P": case "J6P": case "JXP": case "ENTA": case "ENT1": case "ENT2": case "ENT3": case "ENT4": case "ENT5": case "ENT6": case "ENTX": return new FPart(2);
            case "SRAX": case "JNOV": case "JANN": case "J1NN": case "J2NN": case "J3NN": case "J4NN": case "J5NN": case "J6NN": case "JXNN": case "ENNA": case "ENN1": case "ENN2": case "ENN3": case "ENN4": case "ENN5": case "ENN6": case "ENNX": return new FPart(3);
            case "SLC": case "JL": case "JANZ": case "J1NZ": case "J2NZ": case "J3NZ": case "J4NZ": case "J5NZ": case "J6NZ": case "JXNZ": return new FPart(4);
            case "ADD": case "SUB": case "MUL": case "DIV": case "SRC": case "LDA": case "LD1": case "LD2": case "LD3": case "LD4": case "LD5": case "LD6": case "LDX": case "LDAN": case "LD1N": case "LD2N": case "LD3N": case "LD4N": case "LD5N": case "LD6N": case "LDXN": case "STA": case "ST1": case "ST2": case "ST3": case "ST4": case "ST5": case "ST6": case "STX": case "STJ": case "STZ": case "JE": case "JANP": case "J1NP": case "J2NP": case "J3NP": case "J4NP": case "J5NP": case "J6NP": case "JXNP": case "CMPA": case "CMP1": case "CMP2": case "CMP3": case "CMP4": case "CMP5": case "CMP6": case "CMPX": return new FPart(5);
            case "FADD": case "FSUB": case "FMUL": case "FDIV": case "FCMP": case "JG": return new FPart(6);
            case "JGE": return new FPart(7);
            case "JNE": return new FPart(8);
            case "JLE": return new FPart(9);
        }
    }
}