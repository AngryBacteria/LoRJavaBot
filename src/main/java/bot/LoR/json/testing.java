package bot.LoR.json;

import com.opencsv.exceptions.CsvException;

import java.io.*;

public class testing {

    public static void main(String[] args) throws IOException, CsvException {

        CardService cardService = new CardService();

        String regex = "^(?i).*" + "asjdjhkasdkjasd" + ".*$";

        System.out.println(cardService.getCardByNameRegex(regex).size());

    }
}
