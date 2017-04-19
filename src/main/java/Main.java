import com.google.transit.realtime.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args){

        // Load the file
        InputStream stream = null;
        try{
            File feed = new File("src/main/resources/bart-gtfs-rt.pb");
            stream = new FileInputStream(feed);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
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
