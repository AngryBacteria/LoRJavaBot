package bot.LoR.json.subclasses;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ArcheTypeStatMatch {

    @CsvBindByName
    private String archetype;

    @CsvBindByName
    private String games_played;

    @CsvBindByName
    private String playrate;

    @CsvBindByName
    private String winrate;

    public ArcheTypeStatMatch(String archetype, String games_played, String playrate, String winrate) {
        this.archetype = archetype;
        this.games_played = games_played;
        this.playrate = playrate;
        this.winrate = winrate;
    }

    @Override
    public String toString() {
        return "**" + this.archetype + "**\n"
                + "Winrate: " +this.winrate + "%\n"
                + "Matches: " + this.games_played + "\n";
    }
}
