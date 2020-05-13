package top.guoziyang.springframework.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过Url的方式获取资源
 *
 * @author ziyang
 */
public class UrlResource implements Resource {

    private URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    public InputStream getInputStream() throws Exception {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
