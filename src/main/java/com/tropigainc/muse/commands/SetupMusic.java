package com.tropigainc.muse.commands;

import com.tropigainc.muse.data.DataManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SetupMusic implements ICommand
{

    @Override
    public void exec(GuildMessageReceivedEvent event,Object ... params)
    {
        Guild g = event.getGuild();
        String id = DataManager.getMusicId(g);
        if (id != null) return;

        g.createTextChannel("muse-requests").queue(textChannel ->
        {
            DataManager.insertMusicId(g,textChannel.getIdLong());
        });
    }

    @Override
    public String execKey()
    {
        return "setup";
    }

    @Override
    public String getHelp()
    {
        return null;
    }

    @Override
    public CommandClass getCmdClass(){return CommandClass.MUSIC;}
}
