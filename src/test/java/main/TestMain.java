package main;

import org.junit.Test;

import java.io.IOException;

public class TestMain {
    @Test
    public void testAssembly() throws IOException {
        String[] args = {"-a", "src/test/resources/table-of-primes.asm"};
        Main.main(args);
    }
}
