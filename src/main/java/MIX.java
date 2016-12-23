
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MIX {
	public static int[] rA;
	public static int[] rX;
	public static int[][] rI;
	public static int[] rJ;
	public static boolean overflowToggle;
	public static int comparisonIndicator;
	public static Word[] memory;
	
    public static void main(String[] args) throws IOException {
        Assemble.main(args);
        int currentInst = 0;
    }
}