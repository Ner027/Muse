package com.tropigainc.muse.commands;

import com.tropigainc.muse.data.DataManager;
import com.tropigainc.muse.util.DiscordUtil;
import com.tropigainc.muse.util.Util;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.logging.Level;

public class SetPrefix implements ICommand
{
    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        String[] split = Util.splitMessage(event);
        
        if(split.length < 2)
        {
            DiscordUtil.sendWarning(event.getChannel(), "No prefix provided!", Level.FINE);
            return;
        }

        int length = split[1].length();
        
        if (length > 5 || length < 1)
        {
            DiscordUtil.sendWarning(event.getChannel(),"Prefix must be between 1 or 5 chars long!",Level.FINE);
            return;
        }

        DataManager.insertPrefix(event.getGuild(),split[1]);
    }

    @Override
    public String execKey()
    {
        return "setprefix";
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
