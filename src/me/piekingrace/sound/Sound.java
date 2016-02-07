package me.piekingrace.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
	
	public static Clip clip=null;
	public static List<ClipIds> clips = new ArrayList<ClipIds>();
	public static List<Sound> sounds = new ArrayList<Sound>();
	private String url=null;
	
	public static synchronized void playSound(final String url, float volume) {
		
			// The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.=
		      try {
		    	clip = AudioSystem.getClip();
		    	//URL url2 = new URL("http://www.thepierealm.coffeecup.com/java/2D/sound/"+url);
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource("sound/"+url));
		        clip.open(inputStream);
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
		        clip.start(); 
		        clips.add(new ClipIds(clip,0));
		      } catch (Exception e) {
		        //System.err.println(e.getMessage());
		      }
	}
	
	public static synchronized void playSoundPath(final String url, float volume) {
	  try {
		clip = AudioSystem.getClip();
		//URL url2 = new URL("http://www.thepierealm.coffeecup.com/java/2D/sound/"+url);
	    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(url));
	    clip.open(inputStream);
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
	    clip.start(); 
	    clips.add(new ClipIds(clip,0));
	  } catch (Exception e) {
	    //System.err.println(e.getMessage());
	  }
	}
	
	public static void stopSounds() {
		for(ClipIds clip:clips){
			if(clip.getClip().isActive())clip.getClip().stop();
		}
		//for(ClipIds clip:clips){
		//	clip.getClip().close();
		//}
		clips.clear();
	}
	public static synchronized void stopSound(String url) {
		for(Sound sound:sounds){
			if(sound.getUrl().equals(url)) clip.stop();
		}
	}
	public String getUrl(){
		return url;
	}
	
	public static void pause(){
		for(ClipIds clip:clips){
			clip.setPos(clip.getClip().getMicrosecondPosition());
			clip.getClip().stop();
		}
	}
	
	public static void unpause(){
		for(ClipIds clip:clips){
			clip.getClip().setMicrosecondPosition(clip.getPos());
			clip.getClip().start();
		}
	}
	
}

