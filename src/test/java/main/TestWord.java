package main;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestWord {
    @Test
    public void TestApplyWValue() {

    }

    @Test
    public void TestIsValidField() {
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
