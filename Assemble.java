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
        Instruction thisLine;

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
                if(counter == 0) {
                    firstLine = new Instruction(partition[1], new APart(partition[2]), )
                }
            }
        }

        // Evaluate and Replace all expressions
        for(int i = 0; i < file.size(); i ++) {
            fout.println(convertToByte(line[1]).toString());
        }
    }

    public static String[] partitionLine(StringBuilder line) {
        String address;
        String[] partition = new String[3];
        
        partition[0] = getFirstWord(line);
        partition[1] = getFirstWord(line);
        partition[2] = getFirstWord(line);

        return partition;
    }

    public static Object[] partitionAddress(String address) {
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
        
        // Extract IPart
        int index = address.indexOf(",");
        if (index != - 1) {
            partition[1] = new IPart(address.substring(index));
            address = address.substring(0, index);
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
}