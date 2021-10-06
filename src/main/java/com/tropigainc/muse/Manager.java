package com.tropigainc.muse;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class Manager
{
    private static Manager instance;
    private Logger logger;

    public static Manager getInstance()
    {
        if(instance == null)
            instance = new Manager();

        return instance;
    }

    public Logger getLogger()
    {
        if (logger == null)
            logger = Logger.getLogger("muse");

        return logger;
    }
}
