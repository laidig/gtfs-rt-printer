package net.transitdata.gtfsrt;

import com.beust.jcommander.JCommander;
import com.google.transit.realtime.*;
import com.google.protobuf.ExtensionRegistry;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static String fileOrUrl;

    public static void main(String[] args){

        CommandArgs commandArgs = new CommandArgs();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);

        try { fileOrUrl = commandArgs.getFileOrURL(); }
        catch (Exception e) { e.printStackTrace(); }

        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        GtfsRealtimeExtensions.registerExtensions(registry);

        System.out.println("loading " + fileOrUrl);

        InputStream stream;

        if (fileOrUrl.startsWith("http")) {
            stream = StreamUtils.getUrlInputStream(fileOrUrl, commandArgs);
        } else {
            stream = StreamUtils.getFileInputStream(fileOrUrl);
        }

        GtfsRealtime.FeedMessage feedMessage =null;
        try {
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(stream, registry);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (feedMessage != null) {
            System.out.println("feed contains " + feedMessage.getEntityList().size() + " entities");

            for (GtfsRealtime.FeedEntity e : feedMessage.getEntityList()) {
                if (e.hasTripUpdate()) System.out.println(e.getTripUpdate());
                if (e.hasAlert()) System.out.println(e.getAlert());
                if (e.hasVehicle()) System.out.println(e.getVehicle());
            }
    }
}
}
