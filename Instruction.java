public class Instruction extends Word {
    public Instruction(String command, APart address, Byte index, FPart field) {

    }

    public void execute(Word[] memory, Register[] registers, Boolean isOverflow, Integer comparison) {
        
    }


    private Byte getNormalField(String command) {
        
    }

    public static Byte convertToByte(String command) {
        // TODO: convert to upper case....
        switch(command) {
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
}