import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MIX {
	public static void main(String[] args) throws IOException{
        BufferedReader fin = new BufferedReader(new FileReader(args[0]));
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(args[0].substring(0,args[0].indexOf(".")) + ".mix")));
        Assemble.main(args);
        int currentInst = 0;
        
        fin.close();
        fout.close();
	}
}