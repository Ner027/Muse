package com.tropigainc.muse.discord;

import com.tropigainc.muse.commands.CommandManager;
import com.tropigainc.muse.data.DataManager;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class EventListener extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        boolean hasExecuted = false;
        String rawContent = event.getMessage().getContentRaw();
        String[] split = rawContent.split("\\s+");
        String prefix = DataManager.getGuildPrefix(event.getGuild());

        if (rawContent.startsWith(prefix))
        {
            CommandManager.executeCommand(split[0].substring(prefix.length()),event,true);
            hasExecuted = true;
        }

        String id = DataManager.getMusicId(event.getGuild());

        if (event.getChannel().getId().equals(id))
        {
            event.getMessage().delete().queue();
            if (!hasExecuted) CommandManager.executeCommand("play",event,false);
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event)
    {
        DataManager.insertData("RemoveGuild",event.getGuild().getIdLong());
    }

    @Override
    public void onTextChannelDelete(@NotNull TextChannelDeleteEvent event)
    {
        String id = DataManager.getMusicId(event.getGuild());
        if (id == null || !event.getChannel().getId().equals(id)) return;
        DataManager.removeMusicId(event.getGuild());
    }


}
