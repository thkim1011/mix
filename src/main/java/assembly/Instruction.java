package assembly;

/**
 * <b>Instruction Class.</b> So.. I'm not in a very great situation because I'm
 * relying on this as a hack in order to make the Assembly process work again.
 * To briefly summarize what has happened, I removed the original
 * <code>memory</code> static variable from the Assemble class and moved it to
 * the MIX class. Now my mistake was to change the type of <code>memory</code>
 * to Word, as this made all future references malfunction (since future
 * references are calculated after everything has been read). For the current
 * moment, here's a hack to make this work out.
 */
public class Instruction {
	public String myCommand;
	public APart myAPart;
	public IPart myIPart;
	public FPart myFPart;
	
	public Instruction(String command, APart aPart, IPart iPart, FPart fPart) {
		myCommand = command;
		myAPart = aPart;
		myIPart = iPart;
		myFPart = fPart;
	}
}
