/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sound.sampled.*;
import java.io.*;

/**
 *  @(#)SoundDriver.java
 *  
 * @author David Navarro, Nicholas Hernandez
 * @version 1.01 5/6/2014
 * 
 */
public class SoundDriver {

    private Clip[] clips;
    private int[] framePosition;
    private boolean[] isPlaying;
    private FloatControl gainControl;

    public SoundDriver(String[] aClips) {

        clips = new Clip[aClips.length];
        framePosition = new int[aClips.length];
        isPlaying = new boolean[aClips.length];
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    AudioSystem.NOT_SPECIFIED,
                    16, 2, 4,
                    AudioSystem.NOT_SPECIFIED, true);
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            for (int i = 0; i < clips.length; i++) {
                File soundFile = new File(aClips[i]);
                BufferedInputStream bs = new BufferedInputStream(new FileInputStream(soundFile));
                AudioInputStream soundIn = AudioSystem.getAudioInputStream(bs);
                clips[i] = (Clip) AudioSystem.getLine(info);
                clips[i].open(soundIn);
                gainControl = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
            }
            //System.out.println("Audio File Loaded");
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Unsupported Audio File");
        } catch (LineUnavailableException ex) {
            System.out.println("Line Unavailable");
        } catch (IOException ex) {
            System.out.println("IO Error" + ex);
        }
    }

    public void play(int value) {
        clips[value].stop();
        clips[value].setFramePosition(0);
        clips[value].start();
    }

    public void loop(int value) {
        clips[value].stop();
        clips[value].setFramePosition(0);
        clips[value].loop(Clip.LOOP_CONTINUOUSLY);        
    }

    public void stop(int value) {
        clips[value].stop();
    }
    
    public void pause(int value){
        framePosition[value] = clips[value].getFramePosition();
        clips[value].stop();        
    }
    
    public void resume(int value){
        clips[value].setFramePosition(framePosition[value]);
        clips[value].start();
    }
    
    public boolean isPlaying(int value){
        return clips[value].isRunning();
    }

    public void setVolume(float volume) {
        //gainControl.setValue(volume);
    }
}