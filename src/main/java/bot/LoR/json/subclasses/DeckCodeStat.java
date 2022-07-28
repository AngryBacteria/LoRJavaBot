package bot.LoR.json.subclasses;

public class DeckCodeStat {

    private String archeType;
    private String deckCode;
    private String matchesCount;
    private String winRate;

    public DeckCodeStat(String archeType, String deckCode, String matchesCount, String winRate) {
        this.archeType = archeType;
        this.deckCode = deckCode;
        this.matchesCount = matchesCount;
        this.winRate = winRate;
    }

    public String getArcheType() {
        return archeType;
    }

    public String getDeckCode() {
        return deckCode;
    }

    public String getMatchesCount() {
        return matchesCount;
    }

    public String getWinRate() {
        return winRate;
    }

    @Override
    public String toString() {
        return "**" + this.getArcheType() + "**\n"
                + "Winrate: " +this.getWinRate() + "%\n"
                + "Code: " +this.getDeckCode() + "%\n";
    }
}
