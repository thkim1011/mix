public class IPart extends Byte {
    public IPart() {
        super(setValue(0));
    }

    public IPart(String index) {
        Expression e = new Expression(index.substring(1)); // Note that IPart strings should always start with a comma
        super(e.evaluate());
    }
}