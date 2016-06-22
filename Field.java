public class Field extends Byte {
    public Field(int left, int right) {
        super(left*8 + right);
    }
}