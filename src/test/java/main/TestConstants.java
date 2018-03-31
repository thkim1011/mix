package main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestConstants {
    @Test
    public void testCommands() {
        String[] testData = {"LDA", "STX", "ADD", "SUB", "STJ", "ENTA", "INC1", "DEC5"};
        int[] testAnswer = {8, 31, 1, 2, 32, 48, 49, 53};
        for(int i = 0; i < testData.length; i++) {
            assertEquals(Constants.COMMANDS.get(testData[i]).getCode(), testAnswer[i]);
        }
    }

    @Test
    public void testCharacters() {
        assertEquals('0', Constants.CHARACTERS[30]);
        assertEquals('9', Constants.CHARACTERS[39]);
    }
}
