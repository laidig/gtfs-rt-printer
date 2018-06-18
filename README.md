# gtfs-rt-printer

Utility that loads a GTFS-RT from a file or URL and prints the results. It includes extension support, such as for NYCT, and support for several methods of authentication. 

# Download
[JAR file](https://github.com/laidig/gtfs-rt-printer/releases/latest)

# Requirements
* Java 8 
* for Java 7 see older [release](https://github.com/laidig/gtfs-rt-printer/releases/download/0.0.4/gtfs-rt-printer-0.0.4.jar) *note:* this does not support headers.

# Usage

## Loading from a URL
     $ java -jar gtfs-rt-printer-1.0.0.jar http://localhost:8001/tripUpdates
     
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
    
## Loading from a file
    $ java -jar gtfs-rt-printer-1.1.0.jar ../src/test/resources/bart.pb| head
      loading ../src/test/resources/bart.pb
      feed contains 42 entities
      trip {
        trip_id: "1010958SAT"
      }
      stop_time_update {
        stop_sequence: 17
        departure {
          delay: 120
          uncertainty: 30
          

## Sending HTTP GET Headers
    $ java -jar gtfs-rt-printer-1.0.0.jar -header 'Accept: application/x-google-protobuf' -header 'Authorization: apikey ' https://api.transport.nsw.gov.au/v1/gtfs/realtime/sydneytrains | head
    loading https://api.transport.nsw.gov.au/v1/gtfs/realtime/sydneytrains
    Setting header Accept:  application/x-google-protobuf
    Setting header Authorization:  apikey 
    feed contains 299 entities
    trip {
      trip_id: "85-Z.1283.125.128.A.8.51118258"
      schedule_relationship: SCHEDULED
      route_id: "CMB_2c"
    }
    timestamp: 1525537117
    
## Setting HTTP Username and Password
    $ java -jar gtfs-rt-printer-1.1.0.jar -user username -password password http://www.rtd-denver.com/google_sync/TripUpdate.pb
    $ java -jar gtfs-rt-printer-1.2.0.jar -user accessKey -password secretKey https://gtfsapi.metrarail.com/gtfs/raw/tripUpdates.dat
    
Note that reserved characters (e.g. *, ! and & ) need to be escaped (e.g. \\*, \\!, and \\&)
