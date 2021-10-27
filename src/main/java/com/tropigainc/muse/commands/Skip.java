package com.tropigainc.muse.commands;

import com.tropigainc.muse.audio.AudioManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Skip implements ICommand
{

    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        AudioManager.getInstance().getAudioHandler(event.getGuild()).nextTrack();
    }

    @Override
    public String execKey()
    {
        return "skip";
    }

    @Override
    public String getHelp()
    {
        return null;
    }

    @Override
    public CommandClass getCmdClass()
    {
        return null;
    }
}
