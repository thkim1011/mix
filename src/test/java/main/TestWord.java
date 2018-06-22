package main;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestWord {
    @Test
    public void testWord() {
        // Constructor 2
        Word actual1 = new Word(123);
        Word expected1 = new Word(true, 0, 0, 0, 1, 59);
        assertEquals(expected1, actual1);
    }
    @Test
    public void testApplyWValue() {

    }

    @Test
    public void testIsValidField() {
        for (int i = 0; i < 64; i++) {
            int left = i / 8;
            int right = i % 8;
            if (0 <= left && left <= 5 &&
                    0 <= right && right <= 5 &&
                    left <= right) {
                assertTrue(Word.isValidField(i));
            }
            else {
                assertFalse(Word.isValidField(i));
            }
        }
    }
}
