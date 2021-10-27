package com.tropigainc.muse.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public interface ICommand
{
    void exec(GuildMessageReceivedEvent event,Object ... params);
    String execKey();
    String getHelp();
    CommandClass getCmdClass();
}
