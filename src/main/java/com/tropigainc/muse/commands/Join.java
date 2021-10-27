package com.tropigainc.muse.commands;

import com.tropigainc.muse.util.DiscordUtil;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.logging.Level;

public class Join implements ICommand
{
    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        Member member = event.getMember();
        if (member == null) return;

        AudioManager manager = event.getGuild().getAudioManager();

        GuildVoiceState vs = member.getVoiceState();
        if (vs == null) return;

        if (manager.isConnected() && manager.getConnectedChannel() != vs.getChannel())
        {
            DiscordUtil.sendWarning(event.getChannel(),"Muse is already connected to a voice channel!", Level.FINE);
            return;
        }

        if (!vs.inVoiceChannel())
        {
            DiscordUtil.sendWarning(event.getChannel(),"Please join a voice channel first!",Level.FINE);
            return;
        }

        manager.openAudioConnection(vs.getChannel());
    }

    @Override
    public String execKey()
    {
        return "join";
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
