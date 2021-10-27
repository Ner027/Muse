package com.tropigainc.muse.commands;

import com.tropigainc.muse.audio.AudioManager;
import com.tropigainc.muse.util.DiscordUtil;
import com.tropigainc.muse.util.Util;
import com.tropigainc.muse.util.YoutubeUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.logging.Level;

public class Play implements ICommand
{

    @Override
    public void exec(GuildMessageReceivedEvent event, Object... params)
    {
        CommandManager.executeCommand("join",event);

        boolean hasPfx = (boolean) params[0];

        String title = event.getMessage().getContentRaw();

        if (hasPfx) title = title.substring(title.indexOf(' ') + 1);

        if (Util.isUrl(title))
        {
            AudioManager.getInstance().loadAndQueue(title,event.getGuild());
        }
        else
        {
            String url = YoutubeUtil.searchTrack(title);
            if (url == null) DiscordUtil.sendWarning(event.getChannel(),"Something went wrong while trying to load your track!", Level.WARNING);
            AudioManager.getInstance().loadAndQueue(url,event.getGuild());
        }

    }

    @Override
    public String execKey()
    {
        return "play";
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
