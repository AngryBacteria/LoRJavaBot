package bot;
import bot.LoR.json.CardIndexing;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class EnvReader {

    public static final EnvReader ENV_READER = new EnvReader();
    Properties properties;

    public EnvReader() {

        try (InputStream inputStream = CardIndexing.class.getClassLoader().getResourceAsStream("config.properties")){

            this.properties = new Properties();
            properties.load(inputStream);
        }

        catch (IOException e) {
            System.out.println("Couldn't load up the config file correctly");
        }
    }

    public String getDriverLocation() {
        return properties.getProperty("CHROMEDRIVER_PATH");
    }

    public String getImagesPath() {
        return properties.getProperty("IMAGES_PATH");
    }

    public String getDiscordKey() {
        return properties.getProperty("DISCORD_API_KEY");
    }

    public String getRiotKey() {
        return properties.getProperty("RIOT_API_KEY");
    }

    public String getSevenDaysMetaCSV() {
        return properties.getProperty("7DAYS_CSV");
    }

    public String getThreeDaysMetaCSV() {
        return properties.getProperty("3DAYS_CSV");
    }

    public String getJsonFileLocation() {
        return properties.getProperty("JSON_FILES");
    }

    public String getSelFilesPath() {
        return properties.getProperty("SELENIUM_FILES");
    }

    public String getBasePath(){
        return properties.getProperty("BASE_PATH");
    }
}
