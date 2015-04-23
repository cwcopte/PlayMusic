package songs;

import static org.junit.Assert.*;

import org.junit.Test;

public class PitchTest {

	@Test
	public void test() {
		assertEquals(Pitch.getValueOf("A"), Pitch.A);
		assertEquals(Pitch.getValueOf("B"), Pitch.B);
		assertEquals(Pitch.getValueOf("C"), Pitch.C);
		assertEquals(Pitch.getValueOf("D"), Pitch.D);
		assertEquals(Pitch.getValueOf("E"), Pitch.E);
		assertEquals(Pitch.getValueOf("F"), Pitch.F);
		assertEquals(Pitch.getValueOf("G"), Pitch.G);
		assertEquals(Pitch.getValueOf("PT"), null);
		
	}

}
