package dji.lbot2Re.Entities;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dji.lbot2Re.music.MusicWaiter;

import javax.sound.midi.Track;
import java.util.List;

public class MusicOrder {
    public List<AudioTrack> Tracks;
    public long messageId;
    public int gen=0;

    public MusicOrder(long messageId,List<AudioTrack> Tracks){
        this.messageId=messageId;
        this.Tracks= Tracks;
    }
}
