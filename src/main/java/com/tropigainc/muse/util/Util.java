package com.tropigainc.muse.util;

import com.tropigainc.muse.Manager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.stream.Stream;

public class Util
{
    public static String stringFromReader(Reader reader)
    {
        BufferedReader br = new BufferedReader(reader);
        Stream<String> lines = br.lines();
        StringBuilder strBuilder = new StringBuilder();
        lines.forEach(strBuilder::append);
        try
        {
            br.close();
        }
        catch (IOException e)
        {
            Manager.getInstance().getLogger().log(Level.WARNING,"Failed to close a buffered reader",e);
        }
        return strBuilder.toString();
    }


    public static boolean isUrl(String url)
    {
        try
        {
            new URL(url);
            return true;
        }
        catch (MalformedURLException ignored)
        {
            return false;
        }
    }

    public static String[] splitMessage(String string)
    {
        return string.split("\\s+");
    }

    public static String[] splitMessage(GuildMessageReceivedEvent event)
    {
        return splitMessage(event.getMessage().getContentRaw());
    }


}
