public class FutureReference implements Symbol, APart {
    private String myName;
    private int myValue;

    public FutureReference(String name) {
        myName = name;
        myValue = -1;
    }

    public int evaluate() {
        for(int i = 0; i < Assemble.dsymbols.size(); i ++) {
            if(Assemble.dsymbols.get(i) == myName) {
                return Assemble.dsymbols.get(i).evaluate();
            }
        }
        return value;
    }

    public int updateValue(int n) {
        value = n;
    }
}