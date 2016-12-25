package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import register.Register;
import main.Word;

public class TestRegister {
	
	@Test
	public void test() {
		Register r = new Register(5);
		r.setRegister(new Word(true, 1, 2, 3, 4, 5));
		assertEquals(r.getValue(), 17314053);
	}
}
