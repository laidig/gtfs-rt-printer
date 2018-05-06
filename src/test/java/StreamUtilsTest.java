import com.google.protobuf.ExtensionRegistry;
import com.google.transit.realtime.GtfsRealtime;
import net.transitdata.gtfsrt.CommandArgs;
import net.transitdata.gtfsrt.StreamUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StreamUtilsTest {

    @Test
    public void getFileInputStream() {
        InputStream stream = StreamUtils.getFileInputStream("src/test/resources/bart.pb");
        Assert.assertTrue(checkStream(stream));
    }

    @Test
    public void getUrlInputStreamTest() {
        CommandArgs commandArgs = new CommandArgs();
        try {
            // this should be mocked, I know ;/
            InputStream stream = StreamUtils.getUrlInputStream("http://api.bart.gov/gtfsrt/tripupdate.aspx", commandArgs);
            Assert.assertNotNull(stream);
            Assert.assertTrue(checkStream(stream));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("could not get inputstream");
        }
    }

    private boolean checkStream(InputStream stream){
        GtfsRealtime.FeedMessage feedMessage =null;
        try {
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(stream, ExtensionRegistry.getEmptyRegistry());
        } catch (Exception e ){
            e.printStackTrace();
        }
        return feedMessage.hasHeader() && feedMessage.isInitialized();
    }
}