package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Hearts
 * @date 2019/5/18
 * @desc
 */
public class PropertiesUtil {

    public static Properties loadProperties(String location){
        Properties properties = null;
        try {
            properties = loadProperties(Thread.currentThread().getContextClassLoader().getResource(location).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Properties loadProperties(InputStream inputStream){
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
