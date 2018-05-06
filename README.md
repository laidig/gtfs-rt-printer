# file-gtfs-rt-example

Example code that loads a GTFS-RT from a file or URL and prints the results. It includes a set of extensions, such as for NYCT. 

# Usage

     $ java -jar gtfs-rt-printer-0.0.4.jar http://localhost:8001/tripUpdates
     
     loading http://localhost:8001/tripUpdates
     feed contains 459 messages
     trip {
       trip_id: "A20171105WKD_090150_4..S06R"
       start_date: "20180426"
       route_id: "4"
       [transit_realtime.nyct_trip_descriptor] {
         train_id: "04 1507+ WDL/UTI"
         is_assigned: true
         direction: SOUTH
       }
     }
     stop_time_update {
       arrival {
         time: 1524770508
       }
       departure {
         time: 1524770508
       }
       stop_id: "413S"
       [transit_realtime.nyct_stop_time_update] {
         scheduled_track: "1"
       }
     }
     ...
