package simulator.register;

import main.Word;

/**
 * <b>JumpRegister class.</b> The JumpRegister class is a subclass of the
 * Register class and represents a Jump Register.
 * 
 * TODO: Somehow find a way to make only "friend"
 * classes able to access setRegister.
 * 
 * @author Tae Hyung Kim
 *
 */

public class JumpRegister extends Register {
	public JumpRegister() {
		super(2);
	}

	/**
	 * <b>setRegister method</b> This is where this subclass is needed. In a
	 * jump simulator.register, the sign cannot be set, so the method first sets the sign
	 * of the incoming word to true, and then modifies the simulator.register.
	 */
	public void setRegister(Word in) {
		in.setSign(true);
		super.setRegister(in);
	}
}
