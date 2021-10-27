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

        txtCh.sendMessageEmbeds(builder.build()).queue(message ->
        {
            try {message.delete().queueAfter(5, TimeUnit.SECONDS);}
            catch (Exception ignored){}
        });
    }

    public static void mainMessage(TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Nothing playing at the moment!")
                .setColor(Color.MAGENTA)
                .addField("","In communist russia you dont play music, music plays you",false)
                .setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Flag_of_the_Communist_Party_of_the_Philippines_%28alternative_II%29.svg/1200px-Flag_of_the_Communist_Party_of_the_Philippines_%28alternative_II%29.svg.png");

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
