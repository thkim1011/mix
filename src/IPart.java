public class IPart extends Byte {
    public IPart() {
        super(0);
    }

    public IPart(String index) {
        super(index.length() == 0 ? 0 : (new Expression(index.substring(1))).evaluate()); // Note that IPart strings should always start with a comma
        
    }
}