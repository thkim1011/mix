import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize
        Word[] memory = new Word[4000];
        Register[] registers = new Register[9];
        Boolean isOverflow = new Boolean(false);
        Integer comparison = new Integer(0);

        // IO
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("output")));

        // Assemble
        ArrayList<String> file = new ArrayList<String>();
        ArrayList<Variable> variables = new ArrayList<Variable>();

        String line = "";
        while(line != null) {
            line = fout.readLine();
            file.add(line);
            int i = 0, j;
            while(line.charAt(i) == " ") {
                i++;
            }
            j = i;
            while(line.charAt(i) != " ") {
                j++;
            }
            String part1 = line.substring(i, j);
            if(convertToByte(part1) == -1) {

            }
        }
        

    }

}