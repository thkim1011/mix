public class FPart extends Byte {
    public FPart(int left, int right) {
        super(left*8 + right);
    }
}