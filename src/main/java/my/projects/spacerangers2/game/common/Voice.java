package my.projects.spacerangers2.game.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Voice {
	private Clip clip;
	
	public Voice() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public Voice(InputStream voiceContent) {
		this();
		openClip(voiceContent);
	}
	
	public void openClip(InputStream voiceContent) {
		AudioInputStream ais = null;
		try {
			ais = AudioSystem.getAudioInputStream(voiceContent);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		try {
			clip.open(ais);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}

	public boolean setVolume(double volume) {
		if (!clip.isOpen()) {
			return false;
		}
		FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float minVol = vc.getMinimum();
		float maxVol = vc.getMaximum();
		float volumeLevel = minVol + (float)((maxVol-minVol)*volume);
		vc.setValue(volumeLevel);
		return true;
	}
	
	public void awakeThreadOnClipStop(Semaphore s) {
		clip.addLineListener(new ThreadOnEndAwakener(s));
	}
	
	public boolean start() {
		if (!clip.isOpen()) {
			return false;
		}
		clip.start();
		return true;
	}
	
	public void startContinuousPlaying() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public boolean stop() {
		if (!clip.isActive()) {
			return false;
		}
		clip.stop();
		return true;
	}
	
	public boolean close() {
		if (!clip.isOpen()) {
			return false;
		}
		clip.close();
		return true;
	}

	class ThreadOnEndAwakener implements  LineListener{

		private Semaphore semaphore;
		public ThreadOnEndAwakener(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		@Override
		public void update(LineEvent arg0) {
			if (arg0.getType() == LineEvent.Type.STOP) {
				semaphore.release();
			}
		}
	}
}
