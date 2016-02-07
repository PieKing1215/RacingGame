package me.piekingrace.sound;

import javax.sound.sampled.Clip;

public class ClipIds {

	private Clip clip;
	private long pos;
	
	public ClipIds(Clip clip, long pos){
		this.clip=clip;
		this.pos=pos;
	}
	
	public Clip getClip(){
		return clip;
	}
	
	public long getPos(){
		return pos;
	}
	
	public void setPos(long pos2){
		pos=pos2;
	}
	
}
