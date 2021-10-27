package com.tropigainc.muse;

import com.tropigainc.muse.commands.CommandManager;
import com.tropigainc.muse.data.DataManager;
import com.tropigainc.muse.data.SettingsManager;
import com.tropigainc.muse.discord.EventListener;
import com.tropigainc.muse.util.YoutubeUtil;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class Main extends ListenerAdapter
{
    private static String confLoc;
    
    public static void main(String[] args)
    {

        if (args.length < 1)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"Please provide a path to the config file as an argument!");
            System.exit(-1);
        }

        confLoc = args[0];

        try
        {
            JDABuilder.createDefault(SettingsManager.getInstance().getProp("bot_token"))
                    .addEventListeners(new EventListener())
                    .build();
        }
        catch (LoginException e)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"Could not login to discord!",e);
        }

        CommandManager.initCommands();
        DataManager.initDatabases();

    }

    public static String getConfigLocation()
    {
        return confLoc;
    }
}
