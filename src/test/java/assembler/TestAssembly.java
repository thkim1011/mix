package assembler;

import org.junit.Test;
import word.Word;

import static org.junit.Assert.*;

public class TestAssembly {
    @Test
    public void testAssemble() {
        // Set up
        Assembler asm = new Assembler();

        // Basic Commands
        Word actual1 = asm.assemble(" LDA 2000,2(0:3)");
        Word expected1 = new Word(true, 31, 16, 2, 3, 8);
        assertEquals(expected1, actual1);
    }

    @Test
    public void testTokenizeInst() {
        // Standard example with LOC, OP, and ADDRESS.
        String[] actual1 = Assembler.tokenizeInst("L EQU 500");
        String[] expected1 = {"L", "EQU", "500"};
        assertArrayEquals(expected1, actual1);

        // No LOC must be preceded with a space.
        String[] actual2 = Assembler.tokenizeInst(" ADD 2000(4:5)");
        String[] expected2 = {"", "ADD", "2000(4:5)"};
        assertArrayEquals(expected2, actual2);

        // Wrong syntax but nevertheless tokenizable.
        String[] actual3 = Assembler.tokenizeInst("ADD 2000(4:5)");
        String[] expected3 = {"ADD", "2000(4:5)", ""};
        assertArrayEquals(expected3, actual3);

        // Edge case for ALF
        String[] actual4 = Assembler.tokenizeInst("STR ALF  HELLO");
        String[] actual5 = Assembler.tokenizeInst("    ALF   WORL");
        String[] actual6 = Assembler.tokenizeInst("    ALF  D!");
        String[] expected4 = {"STR", "ALF", "HELLO"};
        String[] expected5 = {"", "ALF", " WORL"};
        String[] expected6 = {"", "ALF", "D!   "};
        assertArrayEquals(expected4, actual4);
        assertArrayEquals(expected5, actual5);
        assertArrayEquals(expected6, actual6);

        // One last edge case
        String[] actual7 = Assembler.tokenizeInst("STR ALF HELLO");
        String[] expected7 = {"STR", "ALF", "HELLO"};
        assertArrayEquals(actual7, expected7);
    }

    @Test
    public void testTokenizeAddr() {
        String[] actual1 = Assembler.tokenizeAddr("2000(4:5)");
        String[] expected1 = {"2000", "", "(4:5)"};
        assertArrayEquals(actual1, expected1);

        String[] actual2 = Assembler.tokenizeAddr("BUF0+25");
        String[] expected2 = {"BUF0+25", "", ""};
        assertArrayEquals(actual2, expected2);

        String[] actual3 = Assembler.tokenizeAddr("2000");
        String[] expected3 = {"2000", "", ""};
        assertArrayEquals(actual3, expected3);

        String[] actual4 = Assembler.tokenizeAddr("");
        String[] expected4 = {"", "", ""};
        assertArrayEquals(actual4, expected4);

        String[] actual5 = Assembler.tokenizeAddr("PRIME+L,1");
        String[] expected5 = {"PRIME+L", ",1", ""};
        assertArrayEquals(actual5, expected5);

        String[] actual6 = Assembler.tokenizeAddr("0,4(1:4)");
        String[] expected6 = {"0", ",4", "(1:4)"};
        assertArrayEquals(actual6, expected6);

        String[] actual7 = Assembler.tokenizeAddr("TITLE(PRINTER)");
        String[] expected7 = {"TITLE", "", "(PRINTER)"};
        assertArrayEquals(actual7, expected7);
    }

    @Test
    public void testProcessAPart() {
        // Set up
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("PRIME", 7);
        asm.addDefinedSymbol("L", 3);

        /*
        int actual1 = asm.processAPart("");
        int expected1 = 0;
        assertEquals(expected1, actual1);
        */

    }


    @Test
    public void testIsSymbol() {
        // Correct examples
        assertTrue(Assembler.isSymbol("PRIME"));
        assertTrue(Assembler.isSymbol("TEMP"));
        assertTrue(Assembler.isSymbol("20BY20"));
        assertTrue(Assembler.isSymbol("A"));
        assertTrue(Assembler.isSymbol("ABCDEFGHIJ"));
        assertTrue(Assembler.isSymbol("A1"));
        assertTrue(Assembler.isSymbol("123456789A"));

        // Incorrect examples
        assertFalse(Assembler.isSymbol("123"));
        assertFalse(Assembler.isSymbol("ABCDEFGHIJK"));
        assertFalse(Assembler.isSymbol(""));
        assertFalse(Assembler.isSymbol("*"));
        assertFalse(Assembler.isSymbol("ASDF+"));
    }

    @Test
    public void testIsNumber() {
        // Correct examples
        assertTrue(Assembler.isNumber("0"));
        assertTrue(Assembler.isNumber("12345"));
        assertTrue(Assembler.isNumber("0123456789"));

        // Incorrect examples
        assertFalse(Assembler.isNumber("ABC"));
        assertFalse(Assembler.isNumber(""));
        assertFalse(Assembler.isNumber("12345678901"));
    }

    @Test
    public void testAtomicExpression() {
        // Set up assembler
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("HELLO", 32);
        asm.addDefinedSymbol("WORLD", 24);

        // Successful tests
        String test1 = "HELLO";
        assertTrue(asm.isAtomicExpression(test1));
        int actual1 = asm.parseAtomicExpression(test1);
        int expected1 = 32;
        assertEquals(expected1, actual1);

        String test2 = "WORLD";
        assertTrue(asm.isAtomicExpression(test2));
        int actual2 = asm.parseAtomicExpression(test2);
        int expected2 = 24;
        assertEquals(expected2, actual2);

        String test3 = "12345";
        assertTrue(asm.isAtomicExpression(test3));
        int actual3 = asm.parseAtomicExpression(test3);
        int expected3 = 12345;
        assertEquals(expected3, actual3);

        String test4 = "*";
        assertTrue(asm.isAtomicExpression(test4));
        int actual4 = asm.parseAtomicExpression(test4);
        int expected4 = 0;
        assertEquals(expected4, actual4);

        // Unsuccessful Tests
        assertFalse(asm.isAtomicExpression("HELLOWORLD"));
        assertFalse(asm.isAtomicExpression("1+1"));
        assertFalse(asm.isAtomicExpression("***"));
        assertFalse(asm.isAtomicExpression(""));

    }

    @Test
    public void testIsExpression() {
        // Set Up
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("PRIME", 7);
        asm.addDefinedSymbol("L", 3);

        assertTrue(asm.isExpression("PRIME"));
        assertTrue(asm.isExpression("3"));

        // Test all operations
        assertTrue(asm.isExpression("3+2"));
        assertTrue(asm.isExpression("3-2"));
        assertTrue(asm.isExpression("3*2"));
        assertTrue(asm.isExpression("3/2"));
        assertTrue(asm.isExpression("3//2"));
        assertTrue(asm.isExpression("3:2"));

        // Test more numbers
        assertTrue(asm.isExpression("3+2+1"));
        assertTrue(asm.isExpression("3+2*5"));
        assertTrue(asm.isExpression("3:2:5"));

        // Test with symbols
        assertTrue(asm.isExpression("3+PRIME"));
        assertTrue(asm.isExpression("5+PRIME*2"));
        assertTrue(asm.isExpression("PRIME-PRIME"));

        // Test signs
        assertTrue(asm.isExpression("+3+2"));
        assertTrue(asm.isExpression("-3-2"));
        assertTrue(asm.isExpression("-PRIME+PRIME"));
        assertTrue(asm.isExpression("-PRIME"));

        // Test edge case "//"
        assertTrue(asm.isExpression("PRIME//PRIME//PRIME"));
        assertTrue(asm.isExpression("1//2//3+3+2+5//4"));

        // Bad examples
        assertFalse(asm.isExpression(""));
        assertFalse(asm.isExpression("+-+-+"));
        assertFalse(asm.isExpression("3++3"));
        assertFalse(asm.isExpression("ASDF"));
        assertFalse(asm.isExpression("-ASDF"));
        assertFalse(asm.isExpression("ASDF+ASDF"));
        assertFalse(asm.isExpression("PRIME+ASDF+PRIME"));
        assertFalse(asm.isExpression("PRIME//ASDF//PRIME"));
    }

    @Test
    public void testEvaluateExpression() {
        // Set Up
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("PRIME", 7);
        asm.addDefinedSymbol("L", 3);

        // Test each operator
        int actual1 = asm.evaluateExpression("3+2");
        int expected1 = 5;
        assertEquals(expected1, actual1);

        int actual2 = asm.evaluateExpression("3*2");
        int expected2 = 6;
        assertEquals(expected2, actual2);

        int actual3 = asm.evaluateExpression("3-2");
        int expected3 = 1;
        assertEquals(expected3, actual3);

        int actual4 = asm.evaluateExpression("3/2");
        int expected4 = 1;
        assertEquals(expected4, actual4);

        // More complicated expressions
        int actual5 = asm.evaluateExpression("1+2+3+4+5");
        int expected5 = 15;
        assertEquals(expected5, actual5);

        int actual6 = asm.evaluateExpression("PRIME+L");
        int expected6 = 10;
        assertEquals(expected6, actual6);

        int actual7 = asm.evaluateExpression("PRIME+2*3");
        int expected7 = 27;
        assertEquals(expected7, actual7);

        int actual8 = asm.evaluateExpression("***");
        int expected8 = 0;
        assertEquals(expected8, actual8);
    }

    @Test
    public void testIsWValue() {
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("A", 3);
        asm.addDefinedSymbol("B", 9001);

        assertTrue(asm.isWValue("1"));
        assertTrue(asm.isWValue("A"));
        assertTrue(asm.isWValue("B(0:5)"));
        assertTrue(asm.isWValue("1,-1000(0:2)"));
        assertTrue(asm.isWValue("-1000(0:2),1"));
        assertTrue(asm.isWValue("1000,1234(0:4),2534(0:3),5432(0:2)"));
        assertTrue(asm.isWValue("A,B(0:2)"));
    }
}
