package assembly;

public class FPart {

    private int myField;

    public FPart(String field) {
        // Precondition checking
        if(field.charAt(0) != '(' || field.charAt(field.length() - 1) != ')') {
            throw new IllegalArgumentException("The field must begin with '(' and end with ')'");
        }
    	myField = (new Expression(field.substring(1, field.length() - 1))).evaluate();
    }

    public FPart(int value) {
        myField = value;
    }

    public int getLeft() {
        return myField / 8;
    }
    public int getRight() {
        return myField % 8;
    }
    public int getValue() {
    	return myField;
    }
}