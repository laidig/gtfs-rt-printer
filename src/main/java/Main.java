import com.google.transit.realtime.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    static URL url;
    public static void main(String[] args){

        // Load the file
        InputStream stream = null;
        System.out.println("loading " + args[0]);

        if (args[0].startsWith("http")){
            try {
                url = new URL(args[0]);
                stream = url.openStream();
            } catch (MalformedURLException e) {
                System.out.println("problem with URL " + args[0]);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {

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
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (feedMessage != null) {
            long countOfMessages = feedMessage.getEntityList().size();
            System.out.println("feed contains " + countOfMessages + " messages");


            feedMessage.getEntityList()
                    .stream()
                    .forEach(System.out::println);
        }
    }
}
