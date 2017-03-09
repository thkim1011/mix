// TODO: This is like not good.... why is it implementing APart???
package assembly;

import assembly.atom.Asterisk;
import assembly.atom.AtomicExpression;
import assembly.atom.Number;
import assembly.symbol.DefinedSymbol;
import java.util.HashMap;

/**
 * <b>Expression class.</b>
 */
public class Expression implements APart {

	// Instance Variables
	private int sign;
	private Expression lNode;
	private AtomicExpression value;
	private BinaryOperator op;
	private Expression rNode; // Doubly Linked List Structure

	/**
	 * Constructor - Takes a String exp and returns
	 * 
	 * @param exp
	 */

	public Expression(String exp) {
		this(exp, null);
	}

	public Expression(String exp, Expression left) {
		int i = 0;

		// Check for the sign if it exists
		if (exp.charAt(0) == '+' || exp.charAt(0) == '-') {
			sign = (exp.charAt(0) == '+') ? 1 : -1;
			i++;
		} else {
			sign = 1;
		}
		int j = i + 1;
		char temp = 0;

		// Increment j until the character is an operator
		while (j < exp.length()) {
			temp = exp.charAt(j);
			if (temp == '+' || temp == '-' || temp == '*' || temp == '/' || temp == ':') {
				break;
			}
			j++;
		}

		// This is a new atomic expression
		String atom = exp.substring(i, j);

		// Test if number
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
		if (j == exp.length()) {
			op = null;
			rNode = null;
		}

		// Otherwise construct a new Expression and attach to this
		else {
			if (temp == '/' && exp.charAt(j + 1) == '/') {
				op = new BinaryOperator("//");
				rNode = new Expression(exp.substring(j + 2), this);
			} else {
				op = new BinaryOperator(exp.substring(j, j + 1));
				if (exp.substring(j + 1).equals("")) {
					rNode = null;
				} else {
					rNode = new Expression(exp.substring(j + 1), this);
				}

			}
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
		return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
				|| c == '9';
	}

	public int evaluate(int counter, HashMap<String, DefinedSymbol> definedSymbols) {
		if (rNode == null) {
			return value.evaluate(counter, definedSymbols);
		} else {
			return op.evaluate(sign * value.evaluate(counter, definedSymbols), rNode.evaluate(counter, definedSymbols));
		}
	}

	public String toString() {
		if (rNode.toString() == null) {
			return (sign == 1 ? "+" : "-") + value.toString() + op.toString();
		}
		return (sign == 1 ? "+" : "-") + value.toString() + op.toString() + rNode.toString();
	}
}