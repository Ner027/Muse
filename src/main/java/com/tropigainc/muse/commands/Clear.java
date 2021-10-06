package com.tropigainc.muse.commands;

import com.tropigainc.muse.util.DiscordUtil;
import com.tropigainc.muse.util.Util;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.logging.Level;

public class Clear implements ICommand
{
    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        Member member = event.getMember();
        if (member == null)
            return;

        if (!member.hasPermission(Permission.MESSAGE_MANAGE))
        {
            DiscordUtil.sendWarning(event.getChannel(),"You dont have enough permissions!", Level.WARNING);
            return;
        }

        String[] split = Util.splitMessage(event);

        if (split.length < 2)
        {
            DiscordUtil.sendWarning(event.getChannel(),"Please provide the amount of messages to clear!",Level.FINE);
            return;
        }
        
        try
        {
            int amt = Integer.parseInt(split[1]);
            if (amt > 25)
            {
                DiscordUtil.sendWarning(event.getChannel(),"You can only delete up to 25 messages!",Level.FINE);
                return;
            }
            event.getChannel().getHistory().retrievePast(amt).queue(s -> s.forEach(m -> m.delete().queue()));
        }
        catch (NumberFormatException ignored)
        {
            DiscordUtil.sendWarning(event.getChannel(),"Please provide a valid number!",Level.FINE);
        }


    }

    @Override
    public String execKey()
    {
        return "clear";
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
