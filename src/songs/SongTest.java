package songs;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class SongTest {

	private Song FirstSong;
	private Song SecondSong;
	private Song ThirdSong;
	private Song FourthSong;
	private Song FifthSong;
	private Song SixthSong;
	private Song EighthSong;
	private Song NinethSong;
	private boolean flag;
	private Note[] testNote1;
	private Note[] testNote2;
	private Note[] testNote3;
	private Note[] testNote4;

	@Before
	public void setUp() throws Exception {
		FirstSong = new Song("birthday.txt");
		SecondSong = new Song("HeIsAPirate4.txt");
		ThirdSong = new Song("PopGoesTheWeasel.txt");
		FourthSong = new Song("GameOfThronesTheme.txt");
		FifthSong = new Song("HeIsAPirate2.txt");
		SixthSong = new Song("HeIsAPirate3.txt");
		EighthSong = new Song("birthday1.txt");
		NinethSong = new Song("PopGoesTheWeasel2.txt");
		flag = true;

	}


	@Test
	public void testGetTitle() {
		//System.out.println(firstSong.getTitle());
		assertEquals("something", FirstSong.getTitle());
		assertEquals("He's a Pirate", SecondSong.getTitle());
		assertEquals("Pop Goes the Weasel", ThirdSong.getTitle());
		//System.out.print(FourthSong.toString());
		assertEquals("Game of Thrones", FourthSong.getTitle());
	}

	@Test
	public void testGetArtist() {
		assertEquals("someone", FirstSong.getArtist());
		assertEquals("Hans Zimmer", SecondSong.getArtist());
		assertEquals("Unknown", ThirdSong.getArtist());
		assertEquals("Ramin Djawadi", FourthSong.getArtist());
	}

	@Test
	public void testGetTotalDuration() {
		assertEquals(13.0, FirstSong.getTotalDuration(),.001);
		assertEquals(3.6, SecondSong.getTotalDuration(),.001);
		//System.out.println(FourthSong.getTotalDuration());
		assertEquals(9.8, ThirdSong.getTotalDuration(),.001);
		assertEquals(25.2, FourthSong.getTotalDuration(), .001);
	}

	@Test
	public void testPlay() {

		// test first song, which does not have reaptead section
		FirstSong.play();
		//System.out.println(FirstSong.playedNotes.toString());
		for(int i = 0; i < 25; i++) {
			if (!FirstSong.getNoteList()[i].toString().equals(FirstSong.getPlayedNotes().get(i).toString())) {
				flag = false;
			}
			assertTrue(flag);
		}

		// test the second song, which has one repeated section
		SecondSong.play();
		//System.out.print(SecondSong.playedNotes.toString());
		for(int i = 0; i < 9; i++) { 
			if (!(SecondSong.getNoteList()[i].toString().equals(SecondSong.getPlayedNotes().get(i).toString())) && !(SecondSong.getPlayedNotes().get(i+9).equals(SecondSong.getNoteList()[i]))) {
				flag = false;
			}
		}
		assertTrue(flag);
		for(int i = 9; i < 18; i++) {
			if (!(SecondSong.getNoteList()[i].toString().equals(SecondSong.getPlayedNotes().get(i+9).toString()))) {
				flag = false;
			}
		}

		// test the third song, which do not have reapted section
		ThirdSong.play();
		for(int i = 0; i < 25; i++) {
			if (!ThirdSong.getNoteList()[i].toString().equals(ThirdSong.getPlayedNotes().get(i).toString())) {
				flag = false;
			}
			assertTrue(flag);
		}
		FourthSong.play();
	}

	@Test
	public void testOctaveDown() {
		assertTrue(FirstSong.octaveDown());
		assertTrue(FirstSong.octaveDown());
		assertTrue(FirstSong.octaveDown());
		assertFalse(FirstSong.octaveDown());

		assertTrue(SecondSong.octaveDown());
		assertTrue(SecondSong.octaveDown());
		assertTrue(SecondSong.octaveDown());
		assertFalse(SecondSong.octaveDown());

		assertTrue(ThirdSong.octaveDown());
		assertTrue(ThirdSong.octaveDown());
		assertTrue(ThirdSong.octaveDown());
		assertFalse(ThirdSong.octaveDown());

		assertTrue(FourthSong.octaveDown());
		assertTrue(FourthSong.octaveDown());
		assertTrue(FourthSong.octaveDown());
		assertFalse(FourthSong.octaveDown());

		assertFalse(FifthSong.octaveDown());
	}

	@Test
	public void testOctaveUp() {
		assertTrue(FirstSong.octaveUp());
		assertTrue(FirstSong.octaveUp());
		assertTrue(FirstSong.octaveUp());
		assertTrue(FirstSong.octaveUp());
		assertTrue(FirstSong.octaveUp());
		assertFalse(FirstSong.octaveUp());

		assertTrue(SecondSong.octaveUp());
		assertTrue(SecondSong.octaveUp());
		assertTrue(SecondSong.octaveUp());
		assertTrue(SecondSong.octaveUp());
		assertTrue(SecondSong.octaveUp());
		assertFalse(SecondSong.octaveUp());

		assertTrue(ThirdSong.octaveUp());
		assertTrue(ThirdSong.octaveUp());
		assertTrue(ThirdSong.octaveUp());
		assertTrue(ThirdSong.octaveUp());
		assertTrue(ThirdSong.octaveUp());
		assertFalse(ThirdSong.octaveUp());

		assertTrue(FourthSong.octaveUp());
		assertTrue(FourthSong.octaveUp());
		assertTrue(FourthSong.octaveUp());
		assertTrue(FourthSong.octaveUp());
		assertFalse(FourthSong.octaveUp());

		assertFalse(SixthSong.octaveUp());
	}

	@Test
	public void testChangeTempo() {
		FirstSong.changeTempo(2);
		assertEquals(26.0, FirstSong.getTotalDuration(),.001);
		SecondSong.changeTempo(0.7);
		assertEquals(2.52, SecondSong.getTotalDuration(),.001);
		ThirdSong.changeTempo(1.6);
		assertEquals(15.68, ThirdSong.getTotalDuration(),.001);
		FourthSong.changeTempo(0);
		assertEquals(0, FourthSong.getTotalDuration(), .001);
	}

	@Test
	public void testReverse() {
		int length1 = FirstSong.getNoteList().length;
		Note[] testNote = new Note[length1];
		for (int i = 0; i < length1; i ++) {
			testNote[i] = FirstSong.getNoteList()[length1 - i - 1];
		}
		FirstSong.reverse();
		assertTrue(Arrays.toString(FirstSong.getNoteList()).equals(Arrays.toString(testNote)));

		int length2 = SecondSong.getNoteList().length;
		Note[] testNote2 = new Note[length2];
		for (int i = 0; i < length2; i ++) {
			testNote2[i] = SecondSong.getNoteList()[length2 - i - 1];
		}
		SecondSong.reverse();
		assertTrue(Arrays.toString(SecondSong.getNoteList()).equals(Arrays.toString(testNote2)));

		int length3 = ThirdSong.getNoteList().length;
		Note[] testNote3 = new Note[length3];
		for (int i = 0; i < length3; i ++) {
			testNote3[i] = ThirdSong.getNoteList()[length3 - i - 1];
		}
		ThirdSong.reverse();
		assertTrue(Arrays.toString(ThirdSong.getNoteList()).equals(Arrays.toString(testNote3)));

		int length4 = FourthSong.getNoteList().length;
		Note[] testNote4 = new Note[length4];
		for (int i = 0; i < length4; i ++) {
			testNote4[i] = FourthSong.getNoteList()[length4 - i - 1];
		}
		FourthSong.reverse();
		assertTrue(Arrays.toString(FourthSong.getNoteList()).equals(Arrays.toString(testNote4)));
	}

	@Test
	public void testToString() {

		assertEquals("something\nsomeone\n2.0\n0.25 D 4 NATURAL false\n0.25 D 4 NATURAL false\n0.5 E 4 NATURAL false\n"
				+ "0.5 D 4 NATURAL false\n0.5 G 4 NATURAL false\n", EighthSong.toString());
		assertEquals("Pop Goes the Weasel\nUnknown\n1.8\n0.2 C 4 NATURAL false\n0.4 F 4 NATURAL false\n0.2 F 4 NATURAL false\n0.4 G 4 NATURAL false\n"
				+ "0.2 G 4 NATURAL false\n0.2 A 4 NATURAL false\n0.2 C 5 NATURAL false\n", NinethSong.toString());

	}
}
