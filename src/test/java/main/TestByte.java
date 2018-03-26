package main;

import org.junit.Test;
import word.Byte;

import static org.junit.Assert.assertEquals;

public class TestByte {
    @Test
    public void Test() {
        assertEquals(new Byte(3), new Byte(3));
    }
}
