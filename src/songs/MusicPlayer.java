package songs;

/*
 * Music Player
 *
 * This instructor-provided file implements the graphical user interface (GUI)
 * for the Music Player program and allows you to test the behavior
 * of your Song class.
 */


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.awt.event.ActionEvent;

import javax.management.RuntimeErrorException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;



public class MusicPlayer implements  StdAudio.AudioEventListener {

	// instance variables
	private Song song;
	private boolean playing; // whether a song is currently playing
	private JFrame frame;
	private JFileChooser fileChooser;
	private JTextField tempoText;
	private JSlider currentTimeSlider;

	//these are the two labels that indicate time
	// to the right of the slider
	private JLabel currentTimeLabel;
	private JLabel totalTimeLabel;

	//this the label that says 'welcome to the music player'
	private JLabel statusLabel; 
	//add buttons

	private JButton play;
	private JButton pause;
	private JButton stop;
	private JButton load;
	private JButton reverse;
	private JButton up;
	private JButton down;

	private JButton change;
	private JLabel tempoLabel;
	private JLabel changeSong;
	private JLabel statusSong;
	private JLabel changeResult;
	private boolean reversed;

	private HashMap<String, Double> changeInfo;
	/*
	 * Creates the music player GUI window and graphical components.
	 */
	public MusicPlayer() {
		reversed=false;
		//intial song
		song = null;
		//song = new Song("HeIsAPirate1.txt");
		createComponents();
		doLayout();
		StdAudio.addAudioEventListener(this);
		frame.setVisible(true);

	}



	/*
	 * Called when audio events occur in the StdAudio library. We use this to
	 * set the displayed current time in the slider.
	 */
	public void onAudioEvent(StdAudio.AudioEvent event) {
		// update current time
		if (event.getType() == StdAudio.AudioEvent.Type.PLAY
				|| event.getType() == StdAudio.AudioEvent.Type.STOP) {
			setCurrentTime(getCurrentTime() + event.getDuration());

		}
		//added: enable buttons when stop!
		if(event.getType() == StdAudio.AudioEvent.Type.STOP){
			play.setEnabled(true);
		}
	}

	/*
	public static void main(String[] args) {
		MusicPlayer player= new MusicPlayer();

	}*/
	public void updateSong(boolean up, boolean changeReverse, boolean reverse){

		String text="";
		//currentInfo=changeSong.getText();
		if(changeReverse){
			text+="change octave:"+changeInfo.get("octaveIndex");
			text+="change tempo ratio:"+changeInfo.get("tempo");
			if(reverse){

				text+=" reversed";
			}
		}else{
			double octave=changeInfo.get("octaveIndex");
			//double tempo=changeInfo.get("tempo");

			if(up){
				text+="change octave:"+(octave+1);
				changeInfo.put("octaveIndex", octave+1);

			}else{
				text+="change octave:"+(octave-1);
				changeInfo.put("octaveIndex", octave-1);
			}
			text+="change tempo ratio:"+changeInfo.get("tempo");
			if(reverse){

				text+=" reversed";
			}
		}

		statusSong.setText(text);
	}
	public void updateSong(double ratio){
		String text="change octave:"+changeInfo.get("octaveIndex");
		//only the rate or the exact number(y FOR NOW)?
		double current;
		current=changeInfo.get("tempo");
		changeInfo.put("tempo",current*ratio);
		text+="change tempo ratio:"+current*ratio;
		statusSong.setText(text);
	}

	//can we combine all in one actionListener?
	//or we have to create different class for each one
	//this is an inner class that implements a listener
	class clickListener implements ActionListener{
		//actionPerformed(ActionEvent event) ;
		/*
		 * Called when the user interacts with graphical components, such as
		 * clicking on a button.
		 */
		/*
		public void actionPerformed(ActionEvent arg0) {
			//what happens when you click?
			change.setEnabled(false);
			change.setText("success");
		}*/
		@Override
		public void actionPerformed(ActionEvent event) {

			String cmd = event.getActionCommand();
			if (cmd.equals("Play")) {
				if(song!=null){
					play.setEnabled(false);
					pause.setEnabled(true);
					stop.setEnabled(true);
					change.setEnabled(false);
					tempoText.setEnabled(false);
				}

				playSong();
				//this.playSong();
				//doEnabling();
			} else if (cmd.equals("Pause")) {
				//play.setEnabled(true);
				//pause.setEnabled(false);
				 
				
				StdAudio.setPaused(!StdAudio.isPaused());

				//doEnabling();
			} else if (cmd.equals("Stop")) {
				//change to .equals method instead of ==
				StdAudio.setMute(true);
				StdAudio.setPaused(false);
				//check the status and then do enabling!
				//doEnabling();
				
				stop.setEnabled(false);
				play.setEnabled(true);
				pause.setEnabled(false);
				change.setEnabled(true);
				tempoText.setEnabled(true);
				
			} else if (cmd.equals("Load") ) {
				try {
					loadFile();
					changeInfo=new HashMap<String, Double>();
					changeInfo.put("octaveIndex",0.0);
					changeInfo.put("tempo",1.0);
					/*
					play.setEnabled(true);
					reverse.setEnabled(true);
					up.setEnabled(true);
					down.setEnabled(true);
					*/
					//the ending text might block the buttons on the right!
					//doEnabling();
				} catch (IOException ioe) {
					System.out.println("not able to load from the file");
				}

			} else if (cmd.equals("Reverse")) {
				//TODO - fill this 
				song.reverse();
				reversed=!reversed;
				updateSong(true,true, reversed);
				//song.play();
				//doEnabling();
			} else if (cmd.equals("Up")) {
				//TODO - fill this
				if(song.octaveUp()){
					updateSong(true,false, reversed);
				}else{
					changeResult.setText("changing result: can not increase any further!");
				}
			} else if (cmd.equals("Down")) {
				if(song.octaveDown()){
					updateSong(false,false, reversed);
				}else{
					changeResult.setText("changing result: can not decrease any further!");
				}
			} else if (cmd.equals("Change Tempo")) {
				//stop.setEnabled(false);
				try{
					double ratio;
					ratio=Double.parseDouble(tempoText.getText());
					if(ratio>0){
						updateSong(ratio);
						song.changeTempo(ratio);
					}else{
						changeResult.setText("changing result: Invalid tempo input!");
					}

				}catch (RuntimeErrorException e){
					System.out.println("fail to change the tempo rate.");
				}


			}
		}
		//position moved, is that okay?
		//set cmd for every button
		//why others could be get easily
		//how about the text inside the feild??


	}

	/*
	 * Sets up the graphical components in the window and event listeners.
	 */
	private void createComponents() {
		//TODO - create all your components here.
		// note that you should have already defined your components as instance variables.

		//this the label that says 'welcome to the music player'
		statusLabel=new JLabel("welcome to the music player!",JLabel.CENTER); 
		statusLabel.setFont(new Font("Serif", Font.BOLD, 30));

		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);



		changeSong= new JLabel("Status of the song:");
		statusSong= new JLabel("octave change:0,tempo ratio:1");
		change=new JButton("Change");
		changeResult=new JLabel("changing result: ");
		//change.addActionListener(this.actionPerformed(event));

		//change.addActionListener(new ActionListener());
		change.addActionListener(new clickListener());
		change.setActionCommand("Change Tempo");
		//System.out.println(change.getActionCommand());
		tempoLabel=new JLabel("Tempo");
		//set default text!
		tempoText= new JTextField("enter your tempo. default is 1.");
		//tempoText.setMinimumSize(minimumSize);


		totalTimeLabel=new JLabel();
		//convert double to string
		currentTimeLabel=new JLabel();
		//Place THE CURRENT TIME

		fileChooser=new JFileChooser();

		currentTimeSlider=new JSlider(JSlider.HORIZONTAL,0, 100,1);

		//TODO changed by the length of song!
		currentTimeSlider.setPreferredSize(new Dimension(400,100));
		currentTimeSlider.setOrientation(SwingConstants.HORIZONTAL);
		currentTimeSlider.setPaintTicks(true);
		currentTimeSlider.setPaintLabels(true);
		currentTimeSlider.setMajorTickSpacing(10);
		currentTimeSlider.setMinorTickSpacing(5);



		//add buttons
		play=new JButton("Play");
		play.setToolTipText("Click this button to play the song.");
		play.addActionListener(new clickListener());


		pause=new JButton("Pause");
		pause.setToolTipText("Click this button to stop the song.");
		pause.addActionListener(new clickListener());

		stop=new JButton("Stop");
		stop.setToolTipText("Click this button to stop the song.");
		stop.addActionListener(new clickListener());

		load=new JButton("Load");
		load.setToolTipText("Click this button to open file and load the song.");
		load.addActionListener(new clickListener());

		reverse=new JButton("Reverse");
		reverse.setToolTipText("Click this button to play the song reversely.");
		reverse.addActionListener(new clickListener());

		up=new JButton("Up");
		up.setToolTipText("Click this button to increase the octave by one.");
		up.addActionListener(new clickListener());

		down=new JButton("Down");
		down.setToolTipText("Click this button to decrease the octave by one.");
		down.addActionListener(new clickListener());


		doEnabling();
	}

	/*
	 * Sets whether every button, slider, spinner, etc. should be currently
	 * enabled, based on the current state of whether a song has been loaded and
	 * whether or not it is currently playing. This is done to prevent the user
	 * from doing actions at inappropriate times such as clicking play while the
	 * song is already playing, etc.
	 */
	private void doEnabling() {
		//TODO - figure out which buttons need to enabled
		// begining
		if(playing){
			play.setEnabled(false);
			stop.setEnabled(true);
			load.setEnabled(false);
			reverse.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);
			pause.setEnabled(true);
			//according to the status of the song playing!
		}else{
			play.setEnabled(true);
			stop.setEnabled(false);
			load.setEnabled(true);
			reverse.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);
			pause.setEnabled(false);
		}


	}

	/*
	 * Performs layout of the components within the graphical window. 
	 * Also make the window a certain size and put it in the center of the screen.
	 */
	private void doLayout() {
		//TODO - figure out how to layout the components
		frame = new JFrame();
		frame.setLayout(new BorderLayout ()); 

		Box box2 = new Box(BoxLayout.Y_AXIS);
		box2.add(changeSong);
		box2.add(statusSong);
		Box box1 = new Box(BoxLayout.X_AXIS);

		box1.add(tempoLabel);
		box1.add(tempoText);
		box1.add(change);
		box2.add(box1);
		box2.add(changeResult);
		frame.add(statusLabel,BorderLayout.NORTH);
		frame.add(box2,BorderLayout.SOUTH);

		//CREATE MORE LAYOUT

		JApplet applet=new JApplet();
		applet.setLayout(new FlowLayout());        

		applet.add(currentTimeLabel);
		applet.add(currentTimeSlider);
		applet.add(totalTimeLabel);


		frame.add(applet, BorderLayout.WEST);

		//CREATE MORE LAYOUT
		Box box = new Box(BoxLayout.Y_AXIS);
		frame.add(box,BorderLayout.EAST);        
		box.add(play);
		box.add(pause);  
		box.add(stop);  
		box.add(load);
		box.add(reverse);
		box.add(up);         
		box.add(down);      

		//magic 1 line command that puts everything into an optimal location
		frame.pack();
		//general frame setting
		frame.setTitle("Music Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		//place in the center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

	}

	/*
	 * Returns the estimated current time within the overall song, in seconds.
	 */
	private double getCurrentTime() {
		String timeStr = currentTimeLabel.getText();
		timeStr = timeStr.replace(" /", "");
		try {
			return Double.parseDouble(timeStr);
		} catch (NumberFormatException nfe) {
			return 0.0;
		}
	}

	/*
	 * Pops up a file-choosing window for the user to select a song file to be
	 * loaded. If the user chooses a file, a Song object is created and used
	 * to represent that song.
	 */
	private void loadFile() throws IOException {
		if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selected = fileChooser.getSelectedFile();
		if (selected == null) {
			return;
		}
		statusLabel.setText("Current song: " + selected.getName());
		String filename = selected.getAbsolutePath();
		System.out.println("Loading song from " + selected.getName() + " ...");

		//TODO - create a song from the file
		song=new Song(filename);

		tempoText.setText("1.0");
		setCurrentTime(0.0);
		updateTotalTime();
		System.out.println("Loading complete.");
		System.out.println("Song: " + song);
		doEnabling();
	}

	/*
	 * Initiates the playing of the current song in a separate thread (so
	 * that it does not lock up the GUI). 
	 * You do not need to change this method.
	 * It will not compile until you make your Song class.
	 */
	private void playSong() {
		if (song != null) {
			setCurrentTime(0.0);
			Thread playThread = new Thread(new Runnable() {
				public void run() {
					StdAudio.setMute(false);
					playing = true;
					doEnabling();
					String title = song.getTitle();
					String artist = song.getArtist();
					double duration = song.getTotalDuration();
					System.out.println("Playing \"" + title + "\", by "
							+ artist + " (" + duration + " sec)");
					song.play();
					System.out.println("Playing complete.");
					playing = false;
					doEnabling();
				}
			});
			playThread.start();
		}
	}

	/*
	 * Sets the current time display slider/label to show the given time in
	 * seconds. Bounded to the song's total duration as reported by the song.
	 */
	private void setCurrentTime(double time) {
		double total = song.getTotalDuration();
		time = Math.max(0, Math.min(total, time));
		currentTimeLabel.setText(String.format("%08.2f /", time));
		currentTimeSlider.setValue((int) (100 * time / total));
	}

	/*
	 * Updates the total time label on the screen to the current total duration.
	 */
	private void updateTotalTime() {
		//TODO - fill this
		totalTimeLabel.setText(String.valueOf(song.getTotalDuration())+" sec");
	}




}
