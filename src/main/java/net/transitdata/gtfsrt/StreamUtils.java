package net.transitdata.gtfsrt;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StreamUtils {
    public static InputStream getFileInputStream(String file) {
        try {
            File feed = new File(file);
            return new FileInputStream(feed);
        } catch (Exception e){
            System.out.println("Could not load file " + file);
            return null;
        }
    }

    public static InputStream getUrlInputStream(String arg, CommandArgs commandArgs) {
        try {
            URL url = new URL(arg);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (commandArgs.getHeaders().size() > 0){
                for (String h : commandArgs.getHeaders()) {
                    String[] keyval = h.split(":");
                    if (keyval.length != 2) {
                        throw new IllegalArgumentException("headers must be formatted key: value");
                    }
                    System.out.println("Setting header " + keyval[0] + ": " + keyval[1]);
                    conn.addRequestProperty(keyval[0], keyval[1]);
                }
            }
            conn.setInstanceFollowRedirects(true);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                return stream;
            } else {
                throw new ConnectException();
            }

        } catch (MalformedURLException e) {
            System.out.println("problem with URL " + arg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
