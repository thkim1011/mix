/**
 * <b>Register class.</b> The register interface serves to be an abstraction of
 * the idea of a register. There will be three subclasses, which will be a five
 * byte and sign register (register A and X), a two byte and sign register
 * (register I), and a two byte register (register J). The names of these
 * classes are FullRegister, HalfRegister, and JumpRegister, respectively.
 * 
 * The purpose for creating such an interface is for simplification of the
 * loading operators and storing operators, because, without it, at least one
 * method is required for every single command that could honestly be classified
 * into one method. I figured that this would be VERY bad for debugging in the
 * future.
 * 
 * The primary two methods that each class that implement this interface must
 * have are getWord, which converts the register into a Word object using a
 * given field, and setRegister, which takes a Word object as a input, and will
 * be incorporated into the register.
 * 
 * @author Tae Hyung Kim
 */
package register;
import main.Word;

public interface Register {
	public abstract Word getWord();

	public abstract void setRegister(Word in);
}
