/**
	This is a class made for a more organized playing of audio.
	
	@author Jerold Luther P. Aquino (230413)
    @author Hanzo Ricardo M. Castillo (231365)
	@version March 6, 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Audio {

    /*
     * Creation of Audio class guided by:
     * - https://www.codespeedy.com/how-to-add-audio-on-jswing-in-java/ 
     */

    Clip clip;
    AudioInputStream sound;

    // when calling this, sound file name should be specific and in .wav
    /**
     * Sets the File of the audio to be played.
     * @param soundFileName the file name of the audio.
     */
    public void setFile(String soundFileName) {

        // soundFileName should be the EXACT FORMATING of the FILE NAME and should be in audio format WAV
        try {
            File file = new File(soundFileName);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {

        }
    }

    /**
     * Plays the audio.
     */
    public void play() {
        clip.start();
    }

    /**
     * Loops the audio.
     */
    public void loop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the audio.
     * @throws IOException Gets rid of errors in case the audio is non-existent.
     */
    public void stop() throws IOException {
        sound.close();
        clip.close();
        clip.stop();
    }
    
}
