public class FPart extends Byte {
    public FPart(String field) {
    	super((new Expression(field.substring(1, field.length() - 1)).evaluate()));
    }

    public FPart(int value) {
        super(value);
    }
}