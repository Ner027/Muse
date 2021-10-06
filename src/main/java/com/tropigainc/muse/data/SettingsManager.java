package com.tropigainc.muse.data;

import com.tropigainc.muse.Main;
import com.tropigainc.muse.Manager;
import com.tropigainc.muse.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
@SuppressWarnings("all")

public class SettingsManager
{
    
    private static SettingsManager instance;
    private final HashMap<String,String> data = new HashMap<>();

    public static SettingsManager getInstance()
    {
        if (instance == null)
            instance = new SettingsManager();

        return instance;
    }

    private SettingsManager()
    {
        File f = new File(Main.getConfigLocation());
        Logger logger = Manager.getInstance().getLogger();
        if (!f.exists())
        {
            try
            {
                if (!f.createNewFile())
                {
                    logger.log(Level.SEVERE,"Could not create a new settings file!");
                    System.exit(-1);
                }

                FileWriter fileWriter = new FileWriter(f);
                InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/defaultConf.json"));
                fileWriter.write(Util.stringFromReader(inputStreamReader));
                inputStreamReader.close();
                fileWriter.close();
            }
            catch (IOException e)
            {
                logger.log(Level.SEVERE,"Could not create a new settings file!",e);
                System.exit(-1);
            }
        }

        try
        {
            
            String content = Util.stringFromReader(new FileReader(f));
            JSONObject jObject = new JSONObject(content);
            jObject.keys().forEachRemaining(s -> {
                String key = (String) s;
                try
                {
                    data.put(key,jObject.getString(key));
                }
                catch (JSONException e)
                {
                    logger.log(Level.SEVERE,"Invalid JSON format!Did you fuck up something?",e);
                }
            });
        }
        catch (FileNotFoundException e)
        {
            logger.log(Level.SEVERE,"Could not find the settings file!",e);
            System.exit(-1);
        }
        catch (JSONException e)
        {
            logger.log(Level.SEVERE,"Invalid JSON format!Did you fuck up something?",e);
            System.exit(-1);
        }


    }

    public String getProp(String key)
    {
        String obj = data.get(key);

        if (obj == null)
        {
            Manager.getInstance().getLogger().log(Level.SEVERE,"Property " + key + " could not be found!Please check your config file");
            System.exit(-1);
        }

        return obj;
    }
}
