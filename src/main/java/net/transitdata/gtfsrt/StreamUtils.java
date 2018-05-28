package net.transitdata.gtfsrt;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

            if (commandArgs.getUser() != null && commandArgs.getPassword() != null) {
                String authheader = calculateAuthHeader(commandArgs);
                commandArgs.addHeader(authheader);
            }

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

    private static String calculateAuthHeader(CommandArgs commandArgs){
        String username = commandArgs.getUser();
        String password = commandArgs.getPassword();

        // credit to https://stackoverflow.com/questions/12732422/adding-header-for-httpurlconnection
        String basicAuth = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));
        basicAuth = "Authorization: Basic " +basicAuth;
        return basicAuth;
    }
}
