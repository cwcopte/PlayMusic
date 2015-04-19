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
import java.awt.event.ActionEvent;

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



public class MusicPlayer implements ActionListener, StdAudio.AudioEventListener {

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
	private JButton stop;
	private JButton load;
	private JButton reverse;
	private JButton up;
	private JButton down;

	private JButton change;
	private JLabel tempoLabel;
	/*
	 * Creates the music player GUI window and graphical components.
	 */
	public MusicPlayer() {
		//intial song
		song = null;
		//song = new Song("HeIsAPirate1.txt");
		createComponents();
		doLayout();
		StdAudio.addAudioEventListener(this);
		frame.setVisible(true);
	}

	/*
	 * Called when the user interacts with graphical components, such as
	 * clicking on a button.
	 */
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("Play")) {
			//fill this 
			stop.setEnabled(false);
		} else if (cmd.equals("Pause")) {
			StdAudio.setPaused(!StdAudio.isPaused());
		} else if (cmd == "Stop") {
			StdAudio.setMute(true);
			StdAudio.setPaused(false);
		} else if (cmd == "Load") {
			try {
				loadFile();
			} catch (IOException ioe) {
				System.out.println("not able to load from the file");
			}
		} else if (cmd == "Reverse") {
			//TODO - fill this 
			
		} else if (cmd == "Up") {
			//TODO - fill this
		} else if (cmd == "Down") {
			/*
			//frame.add(fileChooser,BorderLayout.NORTH);
			JFrame frame1= new JFrame();
			frame1.setSize(100, 100);
			frame1.setVisible(true);
			 */
			//TODO - fill this
		} else if (cmd == "Change Tempo") {
			//TODO - fill this
		}
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
	}


	public static void main(String[] args) {
		MusicPlayer player= new MusicPlayer();
		player.createComponents();
	}
	//can we combine all in one actionListener?
	//or we have to create different class for each one
	//this is an inner class that implements a listener
	class changeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//what happens when you click?
			change.setEnabled(false);
			change.setText("success");
		}

	}

	/*
	 * Sets up the graphical components in the window and event listeners.
	 */
	private void createComponents() {
		//TODO - create all your components here.
		// note that you should have already defined your components as instance variables.

		//this the label that says 'welcome to the music player'
		statusLabel=new JLabel("welcome to the music player!",JLabel.CENTER); 
		statusLabel.setFont(new Font("Serif", Font.BOLD, 40));

		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

		change=new JButton("Change");
		change.addActionListener(new changeListener());
		tempoLabel=new JLabel("Tempo");
		//set default text!
		tempoText= new JTextField("enter your tempo. default is 1.");
		//tempoText.setMinimumSize(minimumSize);


		totalTimeLabel=new JLabel();
		//convert double to string
		currentTimeLabel=new JLabel();
		//Place THE CURRENT TIME

		fileChooser=new JFileChooser();

		currentTimeSlider=new JSlider();
		currentTimeSlider.setMinimum(0);

		currentTimeSlider.setMaximum(100);
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
		stop=new JButton("Stop");
		stop.setToolTipText("Click this button to stop the song.");
		load=new JButton("Load");
		stop.setToolTipText("Click this button to open file and load the song.");
		reverse=new JButton("Reverse");
		stop.setToolTipText("Click this button to play the song reversely.");
		up=new JButton("Up");
		stop.setToolTipText("Click this button to increase the octave by one.");
		down=new JButton("Down");
		stop.setToolTipText("Click this button to decrease the octave by one.");

		//ComboBoxes 

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
		// this.setEnabled(false); 
		play.setEnabled(true);
		stop.setEnabled(true);
		load.setEnabled(true);
		reverse.setEnabled(false);
		up.setEnabled(true);
		down.setEnabled(true);
		//according to the status of the song playing!

	}

	/*
	 * Performs layout of the components within the graphical window. 
	 * Also make the window a certain size and put it in the center of the screen.
	 */
	private void doLayout() {
		//TODO - figure out how to layout the components
		frame = new JFrame();
		frame.setLayout(new BorderLayout ()); 

		Box box1 = new Box(BoxLayout.X_AXIS);

		box1.add(tempoLabel);
		box1.add(tempoText);
		box1.add(change);


		frame.add(statusLabel,BorderLayout.NORTH);
		frame.add(box1,BorderLayout.SOUTH);

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
		//WHEN TO SHOW THIS AND HOW?
		//TODO frame.add(fileChooser,BorderLayout.NORTH);

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
		totalTimeLabel.setText(String.valueOf(song.getTotalDuration()));
	}
}
