package assembler;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import assembly.Assemble;

public class TestAssembly {
	@Test
	public void TestAllFiles() throws IOException {
		String[] file = new String[1];
		file[0] = "src/test/mixSource/Maximum.mixal";
		Assemble.main(file);
		BufferedReader Maximum1 = new BufferedReader(new FileReader("src/test/mixSource/Maximum.mix"));
		BufferedReader Maximum2 = new BufferedReader(new FileReader("src/test/mixSource/MaximumExpected.mix"));
		
		boolean isWorking = true;
		while(true) {
			String line1 = Maximum1.readLine();
			String line2 = Maximum2.readLine();
			if(line1 == null || line2 == null) {
				break;
			}
			
			if(!line1.equals(line2)) {
				System.out.println("ERROR: Assembly.main gave \"" + line1 + "\", but \"" + line2 + "\" was expected");
				isWorking = false;
			}
		}
		if(!isWorking) {
			fail();
		}
	}
}
