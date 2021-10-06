package com.tropigainc.muse.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class AudioManager
{

    private static AudioManager instance;
    private final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private final HashMap<Long, TrackHandler> guildPlayers = new HashMap<>();

    private AudioManager()
    {
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        playerManager.registerSourceManager(SoundCloudAudioSourceManager.createDefault());
    }
    

    public TrackHandler getAudioHandler(Guild g)
    {
        long id = g.getIdLong();

        if (!guildPlayers.containsKey(id))
        {
            AudioPlayer player = playerManager.createPlayer();
            TrackHandler handler = new TrackHandler(player);
            player.addListener(handler);
            g.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
            guildPlayers.put(id,handler);
            return handler;
        }
        return guildPlayers.get(id);
    }

    public static AudioManager getInstance()
    {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    public void loadAndQueue(String url,Guild g)
    {
        TrackHandler trackHandler = getAudioHandler(g);
        playerManager.loadItemOrdered(trackHandler, url, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack track)
            {
                trackHandler.queueTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist)
            {

            }

            @Override
            public void noMatches()
            {

            }

            @Override
            public void loadFailed(FriendlyException exception)
            {
                System.err.println("fUcK");
            }
        });

    }
    
}
