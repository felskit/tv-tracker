package android.tvtracker.tools;

import android.tvtracker.models.Episode;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseManager {
    private int userId;
    private String fbUserId;

    private String apiUrl = "http://localhost:2137/api";
    private String charset = java.nio.charset.StandardCharsets.UTF_8.name();
    private Gson gson = new Gson();

    public DatabaseManager(String fbUserId) {
        this.fbUserId = fbUserId; // TODO: request our internal user id?
    }

    public Episode requestEpisodeById(int episodeId) {
        Episode episode = null;
        try {
            String url = apiUrl + "/" + episodeId;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", charset);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream response = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));

                String line;
                StringBuilder content = new StringBuilder();
                while (null != (line = reader.readLine())) {
                    content.append(line);
                }
                episode = gson.fromJson(content.toString(), Episode.class);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return episode;
    }
}
