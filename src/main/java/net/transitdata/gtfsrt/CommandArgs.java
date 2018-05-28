package net.transitdata.gtfsrt;//class for handling command line arguments
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandArgs {
    @Parameter(description = "File or URL", required = true)
    private String fileOrURL;

    @Parameter(names= "-header", description= "Additional HTTP Request header, in format key:value. Can be repeated")
    private List<String> headers = new ArrayList<>();

    @Parameter(names="-user", description = "HTTP Authentication Username")
    private String user;

    @Parameter(names="-password", description = "HTTP Authentication Password")
    private String password;

    public String getUser() { return user; }

    public String getPassword() { return password; }

    public String getFileOrURL() {
        return fileOrURL;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void addHeader(String header) {
        headers.add(header);
    }

    @Override
    public String toString() {
        return "CommandArgs{" +
                "fileOrURL='" + fileOrURL + '\'' +
                ", headers=" + headers +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
