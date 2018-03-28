package simulator;

import main.Word;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSimulator {
    @Test
    public void testLoad() {
        Simulator sim = new Simulator();
        Register rA = sim.getRegisterA();

        // Test LDA
        sim.setMemory(2000, new Word(false, 1, 16, 3, 5, 4));

        sim.load(rA, 2000, 5);
        Word actual1 = rA.getWord();
        Word expected1 = new Word(false, 1, 16, 3, 5, 4);
        assertEquals(expected1, actual1);
    }
}
