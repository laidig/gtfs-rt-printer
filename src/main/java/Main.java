import com.beust.jcommander.JCommander;
import com.google.transit.realtime.*;
import com.google.protobuf.ExtensionRegistry;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//headers....
import com.beust.jcommander.*;

public class Main {
    private static URL url;
    private static String fileOrUrl;
    public static void main(String[] args){
        CommandArgs commandArgs = new CommandArgs();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);

        try {
            System.out.println(commandArgs);
            fileOrUrl = commandArgs.getFileOrURL();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        GtfsRealtimeExtensions.registerExtensions(registry);

        // Load the file
        InputStream stream = null;
        System.out.println("loading " + fileOrUrl);

        if (fileOrUrl.startsWith("http")) try {
            url = new URL(fileOrUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (commandArgs.getHeaders().size() > 0){
                for (String h : commandArgs.getHeaders()) {
                    String[] keyval = h.split(":");
                    if (keyval.length > 2 || keyval.length == 0) {
                        throw new IllegalArgumentException("headers must only have a key and value");
                    }

                    conn.setRequestProperty(keyval[0], keyval[1]);
                }
            }

            stream = conn.getInputStream();

        } catch (MalformedURLException e) {
            System.out.println("problem with URL " + args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        else {

            try {
                File feed = new File(args[0]);
                stream = new FileInputStream(feed);
            } catch (Exception e){
                System.out.println("Could not load file " + args[0]);
            }
        }


        GtfsRealtime.FeedMessage feedMessage =null;
        // Parse file into an object in memory
        try {
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(stream, registry);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (feedMessage != null) {
            long countOfMessages = feedMessage.getEntityList().size();
            System.out.println("feed contains " + countOfMessages + " messages");


            for (GtfsRealtime.FeedEntity e : feedMessage.getEntityList()) {
                if (e.hasTripUpdate()) System.out.println(e.getTripUpdate());
                if (e.hasAlert()) System.out.println(e.getAlert());
                if (e.hasVehicle()) System.out.println(e.getVehicle());
            }
    }
}
}
