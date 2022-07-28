package bot.LoR.json;

import bot.EnvReader;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardIndexing {


    public static Card[] getCardsArrayFromResourcesFile(String fileName){

        try(InputStream inputStream = CardIndexing.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Card[].class);
        }
        catch (IOException e) {
            System.out.println("Parsing didn't work");
            throw new IllegalStateException("Parsing cards didn't work");
        }
    }

    public static Card[] getCardsArrayFromFileAbsolute(String fileName){

        String filePath = EnvReader.ENV_READER.getBasePath() + "json/";

        try(FileReader reader = new FileReader(filePath+fileName);
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, Card[].class);
        }
        catch (IOException e) {
            throw new IllegalStateException("Parsing cards didn't work");
        }
    }

    public static ArrayList<Card> getCardArrayFromArrays(){
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set1-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set2-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set3-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set4-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set5-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set6-en_us.json")));
        return cardArray;
    }

    public static ArrayList<Card> getCardArrayFromArraysAbsolute(){
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set1-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set2-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set3-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set4-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set5-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set6-en_us.json")));
        return cardArray;
    }

    public static void downloadNewestJsons() throws IOException {
        System.out.println("downloading......");
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/set1/en_us/data/set1-en_us.json"), new File("src/main/resources/set1-en_us.json"));
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/set2/en_us/data/set2-en_us.json"), new File("src/main/resources/set2-en_us.json"));
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/set3/en_us/data/set3-en_us.json"), new File("src/main/resources/set3-en_us.json"));
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/set4/en_us/data/set4-en_us.json"), new File("src/main/resources/set4-en_us.json"));
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/set5/en_us/data/set5-en_us.json"), new File("src/main/resources/set5-en_us.json"));
        FileUtils.copyURLToFile(new URL("https://dd.b.pvp.net/latest/core/en_us/data/globals-en_us.json"), new File("src/main/resources/globals-en_us.json"));
        System.out.println("Done");
    }
}