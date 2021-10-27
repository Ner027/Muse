package com.tropigainc.muse.util;

import com.tropigainc.muse.data.SettingsManager;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class YoutubeUtil
{
    public static String searchTrack(String title)
    {
        try
        {
            String formattedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            SettingsManager setMan = SettingsManager.getInstance();

            URL url = new URL(setMan.getProp("youtube_url") +
                            setMan.getProp("youtube_token") +
                            "&q=" + formattedTitle);

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent","Mozilla/5.0");
            con.setRequestMethod("GET");
            String content = Util.stringFromReader(new InputStreamReader(con.getInputStream()));
            JSONObject jObject = new JSONObject(content);
            return "https://www.youtube.com/watch?v=" +
                            jObject.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
