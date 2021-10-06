package com.tropigainc.muse.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;

public class CommandManager
{
    private static final HashMap<String,ICommand> commands = new HashMap<>();
    
    private static void registerCommand(ICommand command)
    {
        if (commands.containsKey(command.execKey())) return;
        commands.put(command.execKey(),command);
    }

    public static void initCommands()
    {
        registerCommand(new SetupMusic());
        registerCommand(new Play());
        registerCommand(new Join());
        registerCommand(new Skip());
        registerCommand(new Leave());
        registerCommand(new SetPrefix());
        registerCommand(new Clear());
    }

    public static boolean executeCommand(String key, GuildMessageReceivedEvent event,Object ... args)
    {
        if (!commands.containsKey(key)) return false;
        commands.get(key).exec(event,args);
        return true;
    }

}
