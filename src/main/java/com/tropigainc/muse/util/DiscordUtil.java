package com.tropigainc.muse.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DiscordUtil
{
    public static void sendWarning(TextChannel txtCh, String text, Level level)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Something went wrong!What could it be?");
        builder.addField("",text,false);

        if (level == Level.FINE) builder.setColor(Color.green);
        else if (level == Level.WARNING) builder.setColor(Color.yellow);
        else if(level == Level.SEVERE) builder.setColor(Color.red);

        txtCh.sendMessageEmbeds(builder.build()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
    }
}
