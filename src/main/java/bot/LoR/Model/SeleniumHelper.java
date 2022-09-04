package bot.LoR.Model;

import bot.EnvReader;
import bot.Main;
import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Set;

public class SeleniumHelper {

    public static void main(String[] args) {
        //Time speed test
        long start = System.currentTimeMillis();
//        try {
//            getPictureFromURL4("CICACAIFFAAQEBIKAECAKDYHAIDBOJJHFQXTKOACAEBAKCACAECRSHIDAEAQKAIBAICQOAQCAYOR4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.setProperty("webdriver.chrome.driver", EnvReader.ENV_READER.getDriverLocation());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized","--window-size=2560,1440","--ignore-certificate-errors","--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://masteringruneterra.com/deck/" + "CUDACBQCEIAQMAZCAEDAYBIBAYDQKAQEA5WYEAICAYCQGBQDAEDAACQDAYDQIIBIAQCAODJ3PGAACAA");

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }

    public static String getPictureFromURL4(String code) throws IOException {

//        File tempfile = new File(EnvReader.ENV_READER.getImagesPath() + code + ".png");
//        if (tempfile.exists()){
//            return EnvReader.ENV_READER.getImagesPath() + code + ".png";
//        }

        System.setProperty("webdriver.chrome.driver", EnvReader.ENV_READER.getDriverLocation());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless","--start-maximized","--window-size=2560,1440","--ignore-certificate-errors","--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://masteringruneterra.com/deck/" + code);

        for (Cookie cookie : Main.cookieList){
            driver.manage().addCookie(cookie);
        }

        driver.get("https://masteringruneterra.com/deck/" + code);
        WebElement ele;
        try {
            ele = driver.findElement(By.cssSelector(".section-deckcards"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occured while taking screenshot will quit");
            driver.quit();
            return "";
        }

        File screenshot = ele.getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = ImageIO.read(screenshot);
        ImageIO.write(bufferedImage , "png", new File(EnvReader.ENV_READER.getImagesPath() + code + ".png"));

        System.out.println("done");
        driver.quit();
        return EnvReader.ENV_READER.getImagesPath() + code + ".png";
    }


    public static void saveCookies(){
        System.setProperty("webdriver.chrome.driver", EnvReader.ENV_READER.getDriverLocation());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--window-size=2560,1440", "--ignore-certificate-errors", "--disable-dev-shm-usage");
        //options.addArguments("user-data-dir="+EnvReader.ENV_READER.getSelFilesPath());
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://masteringruneterra.com");
        Set<Cookie> cookies = driver.manage().getCookies();

        cookies.forEach(System.out::println);
        Gson gson = new Gson();
        try(FileWriter writer = new FileWriter(EnvReader.ENV_READER.getBasePath() + "json/cookies.json"); BufferedWriter bufferedWriter = new BufferedWriter(writer) ) {
            bufferedWriter.write(gson.toJson(cookies));
        }

        catch (IOException e) {
            System.out.println("Writing error jsons");
            e.printStackTrace();
        }
        driver.quit();
    }

    public static Cookie[] readCookies(){

        Cookie[] cookies = null;
        try(FileReader reader = new FileReader(EnvReader.ENV_READER.getBasePath() + "json/cookies.json"); BufferedReader bufferedReader = new BufferedReader(reader)) {
            cookies = new Gson().fromJson(reader, Cookie[].class);
        }
        catch (IOException e) {
            System.out.println("Error when reading cookie jsons");
            e.printStackTrace();
            System.out.println(EnvReader.ENV_READER.getBasePath() + "json/cookies.json");
        }
        System.out.println("Cookie file read");
        return cookies;
    }
}
