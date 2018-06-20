package main;

import org.junit.Test;

import java.io.IOException;

public class TestMain {
    @Test
    public void testAssembly() throws IOException {
        String[] args = {"-a", "src/test/resources/table-of-primes.asm"};
        // Main.main(args);
    }

    @Test
    public void testSimulator() throws IOException {
        String[] args1 = {"-s", "src/test/resources/hello.asm"};
        Main.main(args1);
        String[] args2 = {"-s", "src/test/resources/table-of-primes.asm"};
        Main.main(args2);
    }
}
