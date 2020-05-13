package top.guoziyang.springframework.io;

import java.net.URL;

/**
 * 资源加载器，通过路径加载资源
 *
 * @author ziyang
 */
public class ResourceLoader {

    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }

}
