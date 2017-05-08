package simulator;

import main.Word;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tae Hyung Kim on 2017-05-07.
 */
public class TestLoader {
    @Test
    public void Test() {
        Loader loader = new Loader();

        // Testing partialField method
        Word word = new Word(false, 1, 16, 3, 5, 4);
        assertEquals(loader.partialField(word, 5), new Word(false, 1, 16, 3, 5, 4));
        assertEquals(loader.partialField(word, 13), new Word(true, 1, 16, 3, 5, 4));
        assertEquals(loader.partialField(word, 29), new Word(true, 0, 0, 3, 5, 4));
        assertEquals(loader.partialField(word, 3), new Word(false , 0, 0, 1, 16, 3));
        assertEquals(loader.partialField(word, 36), new Word(true, 0, 0, 0, 0, 5));
        assertEquals(loader.partialField(word, 0), new Word(false, 0,0,0,0,0));
        assertEquals(loader.partialField(word, 9), new Word(true, 0,0,0,0, 1));


    }
}
