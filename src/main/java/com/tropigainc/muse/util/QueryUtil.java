package com.tropigainc.muse.util;

import com.tropigainc.muse.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.stream.Stream;

public class QueryUtil
{
    public static String getQuery(String qName)
    {
        InputStream is = QueryUtil.class.getResourceAsStream("/sql/" + qName + ".sql");

        if (is == null)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"SQL Query not found!");
            System.exit(-1);
        }


        InputStreamReader isr = new InputStreamReader(is);
        String temp = Util.stringFromReader(isr);

        try
        {
            is.close();
            isr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return temp;

    }

}
