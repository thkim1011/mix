package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import assembly.Assemble;

public class MIX {
	public static int[] rA = new int[6];
	public static int[] rX = new int[6];
	public static int[][] rI = new int[6][3];
	public static int[] rJ = new int[2];
	public static boolean overflowToggle = false;
	public static int comparisonIndicator = 0;
	public static int currentInst = 0;
	public static boolean isHalted = false;
	
    public static void main(String[] args) throws IOException {
        Assemble.main(args);
        
        while(true) {
        	//TODO: Find out HOW a MIX computer actually stops itself. Meanwhile, I'm just going to use the HALT.
        	if(isHalted) {
        		break;
        	}
        	Assemble.assembled[currentInst].execute();
        	currentInst++;
        }
    }
}