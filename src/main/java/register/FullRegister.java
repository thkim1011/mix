/**
 * <b>FullRegister.</b> The FullRegister class implements the Register
 * interface and consists of a sign and five bytes (very similar
 * to a Word but not quite). The class also consists of 
 * methods to work with loading, entering, and 
 * storing with the registers A and X. For more information, check
 * the comments on the Register interface.
 *
 * @author Tae Hyung Kim
 */

package register;
import main.Word;

public class FullRegister implements Register {
	private boolean mySign;
	private int[] myBytes;
	
	public FullRegister() {
		mySign = true;
		myBytes = new int[5];
		myBytes[0] = 0;
		myBytes[1] = 0;
		myBytes[2] = 0;
		myBytes[3] = 0;
		myBytes[4] = 0;
	}
	
	public Word getWord() {
	}
	
	public void setRegister(Word in) {
	}

}
