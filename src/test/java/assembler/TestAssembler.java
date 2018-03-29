package assembler;

import org.junit.Test;
import main.Word;

import static org.junit.Assert.*;

public class TestAssembler {
    @Test
    public void testAssemble() {
        // Set up
        Assembler asm = new Assembler();

        // Basic Commands
        Word actual1 = asm.assemble(" LDA 2000,2(0:3)");
        Word expected1 = new Word(true, 31, 16, 2, 3, 8);
        assertEquals(expected1, actual1);

        Word actual2 = asm.assemble(" LDA 2000,2(1:3)");
        Word expected2 = new Word(true, 31, 16, 2, 11, 8);
        assertEquals(expected2, actual2);

        Word actual3 = asm.assemble(" LDA 2000(1:3)");
        Word expected3 = new Word(true, 31, 16, 0, 11, 8);
        assertEquals(expected3, actual3);

        Word actual4 = asm.assemble( " LDA 2000");
        Word expected4 = new Word(true, 31, 16, 0, 5, 8);
        assertEquals(expected4, actual4);

        Word actual5 = asm.assemble(" LDA -2000,4");
        Word expected5 = new Word(false, 31, 16, 4, 5, 8);
        assertEquals(expected5, actual5);

        // EQU
        asm.assemble("ASDF EQU 1000");
        int actual6 = asm.getDefinedSymbol("ASDF");
        int expected6 = 1000;
        assertEquals(expected6, actual6);

        asm.assemble("HELLO EQU 1,-1000(0:2)");
        int actual7 = asm.getDefinedSymbol("HELLO");
        int expected7 = -(1000 * 64 * 64 * 64 + 1);
        assertEquals(expected7, actual7);

        // ORIG
        int counter = asm.getCounter();
        asm.assemble("TABLE ORIG *+100");
        int actual8 = asm.getCounter();
        int expected8 = counter + 100;
        assertEquals(expected8, actual8);

        asm.assemble(" ORIG 2000");
        int actual9 = asm.getCounter();
        int expected9 = 2000;
        assertEquals(expected9, actual9);

        // CON
        Word actual10 = asm.assemble(" CON 1,-1000(0:2)");
        Word expected10 = new Word(false, 15, 40, 0, 0, 1);
        assertEquals(actual10, expected10);

        // ALF
        Word actual11 = asm.assemble(" ALF  HELLO");
        Word actual12 = asm.assemble(" ALF  WORLD");
        Word expected11 = new Word(true, 8, 5, 13, 13, 16);
        Word expected12 = new Word(true, 26, 16, 19, 13, 4);
        assertEquals(expected11, actual11);
        assertEquals(expected12, actual12);

        // Local symbols test
        counter = asm.getCounter();
        Word actual21 = asm.assemble("   NOP 2F");
        Word actual22 = asm.assemble("2H NOP");
        Word actual23 = asm.assemble("   NOP 2B");
        Word expected21 = new Word(true, (counter + 1) / 64, (counter + 1) % 64, 0, 0, 0);
        Word expected22 = new Word(true, 0, 0, 0, 0, 0);
        Word expected23 = new Word(true, (counter + 1) / 64, (counter + 1) % 64, 0, 0, 0);

        assertEquals(expected21, actual21);
        assertEquals(expected22, actual22);
        assertEquals(expected23, actual23);

        // Expect failures for the following
        boolean failed = false;
        try {
            asm.assemble("   NOP 2H");
        } catch(Exception e) {
            failed = true;
        }
        assertTrue(failed);

        failed = false;
        try {
            asm.assemble("2B NOP");
        } catch(Exception e) {
            failed = true;
        }
        assertTrue(failed);

        failed = false;
        try {
            asm.assemble("2F NOP");
        } catch (Exception e) {
            failed = true;
        }
        assertTrue(failed);

        // Literal Constants and void Future References test
        asm.assemble(" ORIG 0");
        Word actual13 = asm.assemble("START LDA =1=");
        asm.assemble(" NOP");
        asm.assemble(" NOP");
        asm.assemble(" NOP");
        Word actual14 = asm.assemble(" LDA ABC");
        Word actual15 = asm.assemble(" LDA QWERTY");
        asm.assemble(" END START");
        Word expected13 = new Word(true, 0, 6, 0, 5, 8);
        Word expected14 = new Word(true, 0, 7, 0, 5, 8);
        Word expected15 = new Word(true, 0, 8, 0, 5, 8);

        assertEquals(expected13, actual13);
        assertEquals(expected14, actual14);
        assertEquals(expected15, actual15);

        Word actual16 = asm.getMemoryAt(6);
        Word expected16 = new Word(true, 0, 0, 0, 0, 1);
        Word actual17 = asm.getMemoryAt(7);
        Word expected17 = new Word(true, 0, 0, 0, 0, 0);
        Word actual18 = asm.getMemoryAt(8);
        Word expected18 = new Word(true, 0, 0, 0, 0, 0);

        assertEquals(expected16, actual16);
        assertEquals(expected17, actual17);
        assertEquals(expected18, actual18);

        // Final test for END
        assertEquals(0, asm.getCounter());
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

        // Vacuous
        Word actual1 = asm.processAPart("");
        Word expected1 = new Word(true, 0, 0, 0, 0, 0);
        assertEquals(expected1, actual1);

        // Expressions
        Word actual2 = asm.processAPart("***");
        Word expected2 = new Word(true, 0, 0, 0, 0, 0);
        assertEquals(expected2, actual2);

        Word actual3 = asm.processAPart("PRIME+L");
        Word expected3 = new Word(true, 0, 10, 0, 0, 0);
        assertEquals(expected3, actual3);

        // Future Reference
        Word actual4 = asm.processAPart("HELLO");
        Word expected4 = new Word(true, 0, 0, 0, 0, 0);
        assertEquals(expected4, actual4);

        // Literal Constants
        Word actual5 = asm.processAPart("=1=");
        Word expected5 = new Word(true, 0, 0, 0, 0, 0);
        assertEquals(expected5, actual5);
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
        asm.addDefinedSymbol("2H", 12);

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

        String test5 = "2B";
        assertTrue(asm.isAtomicExpression(test5));
        int actual5 = asm.parseAtomicExpression(test5);
        int expected5 = 12;
        assertEquals(expected5, actual5);

        // Unsuccessful Tests
        assertFalse(asm.isAtomicExpression("HELLOWORLD"));
        assertFalse(asm.isAtomicExpression("1+1"));
        assertFalse(asm.isAtomicExpression("***"));
        assertFalse(asm.isAtomicExpression(""));
        assertFalse(asm.isAtomicExpression("2H"));
        assertFalse(asm.isAtomicExpression("2F"));
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
        assertTrue(asm.isExpression("*+100"));

        // Test edge case with *
        assertTrue(asm.isExpression("***"));

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
        assertTrue(asm.isWValue("*+100"));
    }

    @Test
    public void testEvaluateWValue() {
        Assembler asm = new Assembler();
        asm.addDefinedSymbol("A", 3);
        asm.addDefinedSymbol("B", 9001);

        Word actual1 = asm.evaluateWValue("1");
        Word expected1 = new Word(true, 0, 0, 0, 0, 1);
        assertEquals(expected1, actual1);

        Word actual2 = asm.evaluateWValue("1,-1000(0:2)");
        Word expected2 = new Word(false, 15, 40, 0, 0, 1);
        assertEquals(expected2, actual2);

        Word actual3 = asm.evaluateWValue("-1000(0:2),1");
        Word expected3 = new Word(true, 0, 0, 0, 0, 1);
        assertEquals(expected3, actual3);
    }

    @Test
    public void testFutureReferences() {
        Assembler asm = new Assembler();

        Word actual1 = asm.assemble(" LDA FUTURE");
        asm.assemble("FUTURE LDA 2000");
        Word expected1 = new Word(true, 0, 1, 0, 5, 8);
        assertEquals(expected1, actual1);

        Word actual2 = asm.assemble(" LDA FUTURE2");
        asm.assemble("FUTURE2 EQU 2000");
        Word expected2 = new Word(true, 31, 16, 0, 5, 8);
        assertEquals(expected2, actual2);
    }
}
