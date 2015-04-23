package songs;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Song {
	String title ;
	String artist ;
	Note[] noteList;
	int linesNum ;
	//populate your song’s array of notes by reading note data from the specified file
	public Song(String filename){

		//read txt file and store music info

		File file = new File(filename);
		ArrayList<Note> collection = new ArrayList<Note>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			title = reader.readLine().trim();
			artist = reader.readLine().trim();
			linesNum =Integer.parseInt( reader.readLine().trim());
			int i=0;
			while (i<linesNum) {
				String line = reader.readLine();
				//System.out.println(line.toString());
				if (line == null) break;
				line = line.trim();
				if (line.equals("")) continue; // ignore possible blank lines
				String[] NoteInfo = line.split(" ");
				// Note(double duration, Pitch pitch, int octave, Accidental accidental, boolean repeat) 
				double duration=Double.parseDouble(NoteInfo[0]);
				Pitch pitch=Pitch.valueOf(NoteInfo[1]);
				// Pitch pitch=new Pitch(NoteInfo[1]);
				//??correct??
				if(pitch.equals(Pitch.R)){
					boolean repeat=Boolean.parseBoolean(NoteInfo[2]);
					collection.add(new Note(duration,  repeat)) ;
				}
				else{
					int octave=Integer.parseInt(NoteInfo[2]);

					Accidental accidental = Accidental.valueOf(NoteInfo[3]); // Accidental.SHARP

					boolean repeat=Boolean.parseBoolean(NoteInfo[4]);
					//add code to catch exception???
					collection.add(new Note(duration,pitch,octave,accidental,repeat));
				}

				i++;
			}
			//convert to array!
			noteList = collection.toArray(new Note[collection.size()]);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// Note(double duration, Pitch pitch, int octave, Accidental accidental, boolean repeat) 
	}
	/**
	 * Returns the title of the song
	 * @return
	 */
	public String getTitle(){

		return title;
	}
	/**
	 * 	Returns the artist.
	 * @return
	 */
	public String getArtist(){
		return artist;
	}
	/**
	 * return the total duration (length) of the song, in seconds
	 * including repeat part!
	 * @return
	 */
	public double getTotalDuration(){
		//need computation
		//not the length but also including the lasting time!! rewrite
		//kind of like the method play? any better way?
		double totalDuration=0;
		//get repeat note
		ArrayList<Integer> repeatPosition=new ArrayList<Integer> ();
		for (int i=0; i<noteList.length;i++){
			if(noteList[i].isRepeat())
			{
				repeatPosition.add(i);
			}
			totalDuration+=noteList[i].getDuration();
		}
		//add length to the total duration
		if(repeatPosition.size()>0){
			for(int j=0;j<repeatPosition.size();j++){
				if(j%2==0){
					for(int start=repeatPosition.get(j);start<=repeatPosition.get(j+1);start++){
						totalDuration+=noteList[start].getDuration();
					}
				}

				/*
				if(j%2!=0){
					totalDuration+=repeatPosition.get(j)-repeatPosition.get(j-1)+1;
				}*/
			}
		}
//how to trim double digit?
		
		return Math.round(totalDuration);
	}
	public static void main(String[] args) {
		//fail, repeat part!
		//HeIsAPirate1.txt
		// GameOfThronesTheme.txt
		
		/*success!
		 * birthday.txt
		 * CastleInTheSky.txt
		 * PopGoesTheWeasel.txt
		 */
		
		Song song=new Song("CastleInTheSky.txt");
		System.out.println(song.toString());
		//how to play the song using StdAudio?
		song.play();
	}
	/**
	 * play your song so that it can be heard on the computer’s
speakers.
	 */
	public void play(){
		//what does input mean?
		double[] input=new double[]{1.0};

		ArrayList<Integer> repeatPosition=new ArrayList<Integer> ();
		//add repeatPosition ArrayList
		for (int i=0; i<noteList.length;i++){
			if(noteList[i].isRepeat()){

				repeatPosition.add(i);
			}
		}
		//play
		int n=1;
		boolean flag=false;
		for (int i=0; i<noteList.length;i++){
			//play repeat part
			if(repeatPosition.size()>=2){
				if(n<repeatPosition.size()){

					if (i==repeatPosition.get(n)){
						noteList[i].play();
						i=repeatPosition.get(n-1);

						n+=2;
						if(n>repeatPosition.size()){
							//whether already reach the end of repeat part
							flag=true;
						}
					}
					//need adjustment here!
					//StdAudio.play(noteList[i], input, noteList[i].getDuration());
					noteList[i].play();
				}
				
				//play the rest no more repeat part
				if(i>repeatPosition.get(repeatPosition.size()-2)&&flag){
					//i>repeatPosition.get(-2)
					//possible?
					noteList[i].play();
					//StdAudio.play(noteList[i], input, noteList[i].getDuration());
					//StdAudio.play(note, input, duration);
					//StdAudio.note(44, 2.0, 1.0);
				}
			}
			else{
				//for the case that there is no repeat part inside song
				//StdAudio.play(noteList[i], input, noteList[i].getDuration());
				noteList[i].play();
			}
		}

		//play(Note note, double[] input, double duration)
		//play(Note note, double[] input, double duration

	}
	/**
	 * modify the state of the notes in your internal array so that
they are all exactly 1 octave lower in pitch than their current state.
	 * @return Return true if this method lowered the octave, 
	 * and false if you hit the special case.
	 */
	public boolean octaveDown(){
		/*
		 *  If any note(s)
in your song are already down at octave 1, then the entire octaveDown call should do
nothing. In such a case, no notes are changed
		 */
		boolean edgeCase=false;
		for (int i=0; i<noteList.length;i++){
			if(noteList[i].getOctave()==1){
				edgeCase=true;
				return false;
			}
		}
		//change the octave if no edge case
		int presentOctave;
		for (int i=0; i<noteList.length;i++){
			presentOctave=noteList[i].getOctave();
			//how to catch exception inside note class?
				noteList[i].setOctave(presentOctave-1);

		}
		return true;
	}
	/**
	 * raises the octave by 1.  special case. We do not allow octaves above 10.
	 * @return
	 */
	public boolean octaveUp(){
		boolean edgeCase=false;
		for (int i=0; i<noteList.length;i++){
			if(noteList[i].getOctave()==10){
				edgeCase=true;
				return false;
			}
		}
		//change the octave if no edge case
		int presentOctave;
		for (int i=0; i<noteList.length;i++){
			presentOctave=noteList[i].getOctave();
			//how to catch exception inside note class?
				noteList[i].setOctave(presentOctave+1);

		}
		return true;
	}
	/**
	 * multiply the duration of each note in your song by the given ratio. 
	 * @param ratio
	 */
	public void changeTempo(double ratio){
		double presentDuration;
		for (int i=0; i<noteList.length;i++){
			presentDuration=noteList[i].getDuration();
			noteList[i].setDuration(presentDuration*ratio);
		}
	}
	/**
	 * reverse the order of the notes in your melody.
	 */
	public void reverse(){
		//reverse noteList
		for(int i=0;i<noteList.length/2;i++){
			Note tempNote=noteList[i];
			noteList[i]=noteList[noteList.length-i-1];
			noteList[noteList.length-i-1]=tempNote;
		}
	}
	/**
	 * for debugging and for the purposes of also being able to write some kind of unit test. 
Arrays.toString returns a string representation of an array.
	 */

	public String toString(){
		//TODO - fill this
		String result=title+"\n"+artist+"\n"+this.getTotalDuration()+"\n";
		for (int i=0; i<noteList.length;i++){
			result+=noteList[i].toString()+"\n";
		}
		//necessary?
		System.out.println(Arrays.toString(noteList));
		return result;
	}
}
