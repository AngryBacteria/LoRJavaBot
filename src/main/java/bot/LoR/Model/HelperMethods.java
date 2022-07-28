package bot.LoR.Model;

import bot.LoR.json.subclasses.ArcheTypeStatMatch;
import bot.LoR.json.subclasses.DeckCodeStat;
import bot.Main;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelperMethods {

    public static boolean isOwner(long id){
        return id == Main.ownerID;
    }

    public static ArrayList<ArcheTypeStatMatch> parseMatchesCSV(String csvPath){

        ArrayList<ArcheTypeStatMatch> archeTypeStatMatches = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))){
            List<String[]> r = reader.readAll();
            r.forEach(x -> archeTypeStatMatches.add(new ArcheTypeStatMatch(x[0], x[1], x[2], x[3])));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return archeTypeStatMatches;
    }


    public static ArrayList<DeckCodeStat> parseDeckStatCSV(String csvPath){
        ArrayList<DeckCodeStat> deckStatMatches = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))){

            List<String[]> r = reader.readAll();
            r.forEach(deck -> deckStatMatches.add(new DeckCodeStat(deck[0], deck[1], deck[2], deck[3])));

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return deckStatMatches;
    }
    }
