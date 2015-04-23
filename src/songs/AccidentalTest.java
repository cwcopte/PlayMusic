package songs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccidentalTest {
	
	@Test
	public void test() {
		assertEquals(Accidental.getValueOf("SHARP"), Accidental.SHARP);
		assertEquals(Accidental.getValueOf("FLAT"), Accidental.FLAT);
		assertEquals(Accidental.getValueOf("NATURAL"), Accidental.NATURAL);
		// test null output situation
		assertEquals(Accidental.getValueOf("AC"), null);
		
	}

}
