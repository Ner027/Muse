package com.tropigainc.muse.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.LinkedList;

public class TrackHandler extends AudioEventAdapter
{

    private final AudioPlayer parentPLayer;

    private final LinkedList<AudioTrack> tracks = new LinkedList<>();

    public TrackHandler(AudioPlayer player)
    {
        parentPLayer = player;
    }

    public void queueTrack(AudioTrack track)
    {
        if(!parentPLayer.startTrack(track,true))
            tracks.add(track);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        if (endReason.mayStartNext)
            nextTrack();
    }

    public void nextTrack()
    {
        if (tracks.isEmpty()) parentPLayer.startTrack(null,false);
        else parentPLayer.startTrack(tracks.pop(),false);
    }

    public void emptyQueue()
    {
        tracks.clear();
        parentPLayer.startTrack(null,false);
    }
}
