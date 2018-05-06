package net.transitdata.gtfsrt;//class for handling command line arguments
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandArgs {
    @Parameter(description = "File or URL", required = true)
    private String fileOrURL;

    @Parameter(names= "-header", description= "Additional HTTP Request header, in format key:value. Can be repeated")
    private List<String> headers = new ArrayList<>();

    public String getFileOrURL() {
        return fileOrURL;
    }

    public List<String> getHeaders() {
        return headers;
    }

    //debug only
    @Override
    public String toString() {
        return "net.transitdata.gtfsrt.CommandArgs{" +
                "fileOrURL='" + fileOrURL + '\'' +
                ", headers=" + headers +
                '}';
    }
}
