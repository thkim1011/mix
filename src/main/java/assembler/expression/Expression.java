// TODO: This is like not good.... why is it implementing APart???
package assembler.expression;

import assembler.APart;
import assembler.Assemble;
import assembler.BinaryOperator;
import assembler.atom.Asterisk;
import assembler.atom.AtomicExpression;
import assembler.atom.Number;
import assembler.symbol.DefinedSymbol;

/**
 * <b>Expression class.</b>
 */
public class Expression implements APart {

	// Instance Variables
	private int sign;
	private Expression lNode;
	private AtomicExpression value;
	private BinaryOperator op;

	/**
	 * Constructor - Takes a String exp and returns
	 * 
	 * @param exp
	 */

	public Expression(String exp) {
		if (exp.length() == 0) {
			sign = 1;
			lNode = null;
			value = new Number(0);
			op = null;
			return;
		}

		int i = exp.length() - 1;

		// Decrement i until the character is an operator
		while (i >= 0) {

			char temp = exp.charAt(i);
			if (temp == '+' || temp == '-' || temp == '*' || temp == '/' || temp == ':') {
				break;
			}
			i--;
		}


		// This is a new atomic expression
		String atom = exp.substring(i + 1);



		String operator;
		if(i > 0 && exp.charAt(i) == '/' && exp.charAt(i - 1) == '/') {
			i --;
			operator = exp.substring(i , i + 2);
		}
		else if (i > 0) {
			operator = exp.substring(i, i + 1);
		}
		else {
			// No operator exists
			operator = null;
		}






		// Test if number
		// TODO: Replace this with REGEX
		boolean isNumber = true;
		for (int k = 0; k < atom.length(); k++) {
			if (!(isNumber(atom.charAt(k)))) {
				isNumber = false;
				break;
			}
		}
		if (atom.length() == 0) {
			isNumber = false;
		}

		// If asterisk
		if (atom.equals("*")) {
			value = new Asterisk();
		}
		// If number
		else if (isNumber) {
			value = new Number(Integer.parseInt(atom));
		}
		// If variable
		else {

			value = new DefinedSymbol(atom, -1);
		}









		// If at the end of string, then there is no op or node
		if (i == -1) {
			lNode = null;
			sign = 1;
			return;
		}

		else if (i == 0) {
			lNode = null;
			if (exp.charAt(i) == '+') {
				sign = 1;
			}
			else if (exp.charAt(i) == '-') {
				sign = -1;
			}
			else {
				throw new IllegalArgumentException("Inexistent Sign.");
			}
			return;
		}
		// Otherwise construct a new Expression and attach to this
		else {
			op = new BinaryOperator(operator);
			lNode = new Expression(exp.substring(0, i));
		}
	}

	/**
	 * isNumber private method
	 * 
	 * @param c
	 *            Character to be checked
	 * @return Returns true if is a number and false otherwise
	 */
	private boolean isNumber(char c) {
		return c == '0' ||
				c == '1' ||
				c == '2' ||
				c == '3' ||
				c == '4' ||
				c == '5' ||
				c == '6' ||
				c == '7' ||
				c == '8'
				|| c == '9';
	}

	public int evaluate(Assemble assembler) {

		if (lNode == null) {
			return sign * value.evaluate(assembler);
		} else {
			return op.evaluate(value.evaluate(assembler),
					lNode.evaluate(assembler));
		}
	}

	public String toString() {
		if (lNode.toString() == null) {
			return (sign == 1 ? "+" : "-") + value.toString() + op.toString();
		}
		return (sign == 1 ? "+" : "-") + lNode.toString() + op.toString()  + value.toString() ;
	}
}