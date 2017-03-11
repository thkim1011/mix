package assembler;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestAssembly {
	@Test
	public void TestAllFiles() throws IOException {
	    String[] fileName = {
	            "Maximum",
                "TableOfPrimes"
        };

		String[] fileIn = new String[fileName.length];
		String[] fileOut = new String[fileName.length];
		String[] fileExpected = new String[fileName.length];
		boolean[] success = new boolean[fileName.length];

		for(int i = 0; i < fileName.length; i++) {
		    fileIn[i] = "src/test/mixSource/" + fileName[i] + ".mixal";
		    fileOut[i] =  "src/test/mixSource/" + fileName[i] + ".mix";
		    fileExpected[i] =  "src/test/mixSource/" + fileName[i] + "Expected.mix";
        }

        for(int i = 0; i < fileName.length; i++) {
		    System.out.println("Test " + i + ": " + fileName[i]);
            Assemble assembler = new Assemble(fileIn[i], fileOut[i]);
            BufferedReader Maximum1 = new BufferedReader(new FileReader(fileOut[i]));
            BufferedReader Maximum2 = new BufferedReader(new FileReader(fileExpected[i]));
            boolean isWorking = true;
            int counter = 0;
            while(true) {
                String line1 = Maximum1.readLine();
                String line2 = Maximum2.readLine();
                if(line1 == null || line2 == null) {
                    break;
                }

                if(!line1.equals(line2)) {
                    System.out.println("ERROR: (Line " + counter + ") Assembly.main gave \"" + line1 + "\", but \"" + line2 + "\" was expected");
                    isWorking = false;
                }
                counter++;
            }
        }
	}
}
