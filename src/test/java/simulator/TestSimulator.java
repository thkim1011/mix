package simulator;

import main.Word;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSimulator {
    @Test
    public void testLoad() {
        Simulator sim = new Simulator();
        Register rA = sim.getRegisterA();
        Register rX = sim.getRegisterX();
        Register rI1 = sim.getIndexRegister(1);
        Register rJ = sim.getJumpRegister();

        // Test LDA
        sim.setMemory(2000, new Word(false, 1, 16, 3, 5, 4));

        sim.load(rA, 2000, 5, false);
        Word actual1 = rA.getWord();
        Word expected1 = new Word(false, 1, 16, 3, 5, 4);
        assertEquals(expected1, actual1);

        sim.load(rA, 2000, 8+5, false);
        Word actual2 = rA.getWord();
        Word expected2 = new Word(true, 1, 16, 3, 5, 4);
        assertEquals(expected2, actual2);

        sim.load(rA, 2000, 3*8+5, false);
        Word actual3 = rA.getWord();
        Word expected3 = new Word(true, 0, 0, 3, 5, 4);
        assertEquals(expected3, actual3);

        sim.load(rA, 2000, 3, false);
        Word actual4 = rA.getWord();
        Word expected4 = new Word(false, 0, 0, 1, 16, 3);
        assertEquals(expected4, actual4);

        sim.load(rA, 2000, 4*8+4, false);
        Word actual5 = rA.getWord();
        Word expected5 = new Word(true, 0, 0, 0, 0, 5);
        assertEquals(expected5, actual5);

        sim.load(rA, 2000, 0, false);
        Word actual6 = rA.getWord();
        Word expected6 = new Word(false, 0, 0, 0, 0, 0);
        assertEquals(expected6, actual6);

        sim.load(rA, 2000, 8+1, false);
        Word actual7 = rA.getWord();
        Word expected7 = new Word(true, 0, 0, 0, 0, 1);
        assertEquals(expected7, actual7);

        // Test LDX
        sim.setMemory(1000, new Word(true, 1, 2, 3, 4, 5));

        sim.load(rX, 1000, 0, false);
        Word actual8 = rX.getWord();
        Word expected8 = new Word();
        assertEquals(expected8, actual8);

        sim.load(rX, 1000, 5, false);
        Word actual9 = rX.getWord();
        Word expected9 = new Word(true, 1, 2, 3, 4, 5);
        assertEquals(expected9, actual9);

        sim.load(rX, 1000, 4, false);
        Word actual10 = rX.getWord();
        Word expected10 = new Word(true, 0, 1, 2, 3, 4);
        assertEquals(expected10, actual10);

        // Test LD1
        sim.load(rI1, 1000, 2, false);
        Word actual11 = rI1.getWord();
        Word expected11 = new Word(true, 0, 0, 0, 1, 2);
        assertEquals(expected11, actual11);

        // Test Jump

        // Test Negative Loads
    }

    @Test
    public void testStore() {
        Simulator sim = new Simulator();
    }
}
