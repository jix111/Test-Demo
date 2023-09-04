package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    public static String URL;
    public static final int PORT = 9090;
    public static void init(){
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\config.ini")));
            URL =  properties.getProperty("URL");
            System.out.println("PORT - " + Config.PORT);
            System.out.println("URL - " + Config.URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
