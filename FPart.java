public class FPart extends Byte {
    public FPart(String field) {
        super(left*8 + right);
    }
    
    public FPart(int value) {
        super(value);
    }
}