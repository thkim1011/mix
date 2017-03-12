package assembler.symbol;

public interface Symbol {
    public abstract String getName();
    
    public static boolean isValidSymbolName(String name) {
    	if (name.length() == 0 || name.length() > 10) {
    		return false;
    	}
    	return name.matches("([A-Z]|[0-9])*");
    }
}