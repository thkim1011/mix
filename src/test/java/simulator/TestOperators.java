package simulator;

import org.junit.Test;
import main.Word;
import main.MIX;

public class TestOperators {
	@Test
	public void TestLDr() {
		System.out.println("***************************");
		System.out.println("* Testing Load Operators  *");
		System.out.println("***************************");
		
		System.out.println("\nI. Testing LDA");
		
		System.out.print("\tA. Without Indices");
		
		MIX.memory[0] = new Word(true, 1, 2, 3, 4, 5);
		Word inst = new Word(true, 0, 0, 0, 5, 8);
		inst.execute();
		
		
	}
}
