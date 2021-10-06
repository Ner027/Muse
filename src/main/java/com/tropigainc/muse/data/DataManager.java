package com.tropigainc.muse.data;


import com.tropigainc.muse.Manager;
import com.tropigainc.muse.util.QueryUtil;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager
{
    private static Connection connection;
    private static final Jedis jedis = new Jedis();

    public static void initDatabases()
    {
        insertData("InitDatabase");
    }

    @NotNull private static Connection getConnection()
    {
        if (connection == null)
        {
            SettingsManager settings = SettingsManager.getInstance();
            try
            {
                connection = DriverManager.getConnection(settings.getProp("postgres_url"),settings.getProp("postgres_user"),settings.getProp("postgres_password"));
            }
            catch (SQLException e)
            {
                Manager.getInstance().getLogger().log(Level.SEVERE,"An error occurred while connecting to a " +
                        "database!",e);
                System.exit(-1);
            }
        }

        return connection;
    }


    private static PreparedStatement prepareStatement(String qName,Object ... params)
    {
        try
        {
            PreparedStatement pStat = getConnection().prepareStatement(QueryUtil.getQuery(qName));
            for (int i = 0; i < params.length; i++)
            {
                pStat.setObject(i + 1,params[i]);
            }
            return pStat;
        }
        catch (SQLException e)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"Could not prepare SQL statement",e);
            return null;
        }
    }

    @SuppressWarnings("all")
    public static  <T> T getData(Class<T> type,String qName,String colName,Object ... params)
    {
        PreparedStatement pStat = prepareStatement(qName, params);
        Logger logger =  Manager.getInstance().getLogger();

        if (pStat == null) return null;

        try
        {
            ResultSet set = pStat.executeQuery();
            while (set.next())
            {
                Object obj = set.getObject(colName);
                if (!type.isInstance(obj)) return null;
                return (T) obj;
            }
        }
        catch (SQLException e)
        {
            logger.log(Level.WARNING,"Could not execute query!",e);
        }
        return null;
    }

    public static boolean insertData(String queryName,Object ... params)
    {
        PreparedStatement preparedStatement = prepareStatement(queryName,params);
        if (preparedStatement == null) return false;

        try
        {
            return preparedStatement.execute();
        }
        catch (SQLException e)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"An error occurred while executing an SQL request",e);
            return false;
        }
    }
    
    
    public static String getMusicId(Guild g)
    {
        return tryGetData(g.getId() + "/musicID","GetMusicChannel","music_id",g.getIdLong());
    }
    
    
    private static String tryGetData(String path,String qName,String colName,Object ... params)
    {
        String data = jedis.get(path);
        if (data == null)
        {
            data = getData(String.class,qName,colName,params);
            if (data != null) jedis.set(path,data);
        }
        return data;
    }

    public static void insertMusicId(Guild g,long id)
    {
        cacheData(id,g.getId() + "/musicID","SetupMusic",g.getIdLong(),id,id);
    }

    public static void removeMusicId(Guild g)
    {
        removeData(g.getId() + "/musicID","RemoveMusicChannel",g.getIdLong());
    }

    private static void removeData(String path,String qName,Object ... params)
    {
        jedis.del(path);
        insertData(qName,params);
    }

    private static void cacheData(Object val,String path,String qName,Object ... params)
    {
        jedis.set(path,String.valueOf(val));
        insertData(qName,params);
    }

    public static String getGuildPrefix(Guild g)
    {
        String prefix = tryGetData(g.getId() + "/prefix","GetPrefix","prefix",g.getIdLong());
        if (prefix == null) return SettingsManager.getInstance().getProp("default_prefix");
        return prefix;
    }

    public static void insertPrefix(Guild g,String prefix)
    {
        cacheData(prefix,g.getId() + "/prefix","InsertPrefix",g.getIdLong(),prefix,prefix);
    }

}
