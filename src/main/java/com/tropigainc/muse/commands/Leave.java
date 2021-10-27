package com.tropigainc.muse.commands;

import com.tropigainc.muse.audio.AudioManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Leave implements ICommand
{

    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        Guild g = event.getGuild();
        AudioManager.getInstance().getAudioHandler(g).emptyQueue();
        g.getAudioManager().closeAudioConnection();
    }

    @Override
    public String execKey()
    {
        return "leave";
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
