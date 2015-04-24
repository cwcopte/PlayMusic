package songs;

import static org.junit.Assert.*;

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
	
	
	@Before
	public void setUp() throws Exception {
		FirstSong = new Song("birthday.txt");
		SecondSong = new Song("HeIsAPirate1.txt");
		ThirdSong = new Song("PopGoesTheWeasel.txt");
		FourthSong = new Song("GameOfThronesTheme.txt");
		FifthSong = new Song("HeIsAPirate2.txt");
		SixthSong = new Song("HeIsAPirate3.txt");
		EighthSong = new Song("birthday1.txt");
		NinethSong = new Song("PopGoesTheWeasel2.txt");
		
	}


	@Test
	public void testGetTitle() {
		//System.out.println(firstSong.getTitle());
		assertEquals("something", FirstSong.getTitle());
		assertEquals("He's a Pirate", SecondSong.getTitle());
		assertEquals("Pop Goes the Weasel", ThirdSong.getTitle());
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
		assertEquals(24.0, FourthSong.getTotalDuration(), .001);
	}

	@Test
	public void testPlay() {
		fail("Not yet implemented");
	}

	@Test
	public void testOctaveDown() {
		assertTrue(FirstSong.octaveDown());
		assertTrue(SecondSong.octaveDown());
		assertTrue(ThirdSong.octaveDown());
		assertTrue(FourthSong.octaveDown());
		assertFalse(FifthSong.octaveDown());
	}

	@Test
	public void testOctaveUp() {
		assertTrue(FirstSong.octaveUp());
		assertTrue(SecondSong.octaveUp());
		assertTrue(ThirdSong.octaveUp());
		assertTrue(FourthSong.octaveUp());
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
		//fail("Not yet implemented");
		Note[] noteListTest = FirstSong.noteList;
		FirstSong.reverse();
		FirstSong.reverse();
		assertTrue(FirstSong.noteList.equals(noteListTest));
		Note[] noteListTest2 = SecondSong.noteList;
		SecondSong.reverse();
		SecondSong.reverse();
		assertTrue(SecondSong.noteList.equals(noteListTest2));
		Note[] noteListTest3 = ThirdSong.noteList;
		ThirdSong.reverse();
		ThirdSong.reverse();
		assertTrue(ThirdSong.noteList.equals(noteListTest3));
		Note[] noteListTest4 = FourthSong.noteList;
		FourthSong.reverse();
		FourthSong.reverse();
		assertTrue(FourthSong.noteList.equals(noteListTest4));
	}

	@Test
	public void testToString() {
		//fail("Not yet implemented");
		System.out.print(NinethSong.toString());
		assertEquals("something\nsomeone\n2.0\n0.25 D 4 NATURAL false\n0.25 D 4 NATURAL false\n0.5 E 4 NATURAL false\n"
				+ "0.5 D 4 NATURAL false\n0.5 G 4 NATURAL false\n", EighthSong.toString());
		assertEquals("Pop Goes the Weasel\nUnknown\n1.8\n0.2 C 4 NATURAL false\n0.4 F 4 NATURAL false\n0.2 F 4 NATURAL false\n0.4 G 4 NATURAL false\n"
				+ "0.2 G 4 NATURAL false\n0.2 A 4 NATURAL false\n0.2 C 5 NATURAL false\n", NinethSong.toString());
	}
}
