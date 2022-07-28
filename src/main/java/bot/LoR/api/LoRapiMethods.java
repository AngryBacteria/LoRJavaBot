package bot.LoR.api;

import bot.EnvReader;
import bot.LoR.json.Card;
import bot.LoR.json.CardService;
import bot.LoR.json.subclasses.CardAndAmount;
import bot.LoR.json.subclasses.Region;
import bot.Main;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RuneterraShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lor.LoRDeckCode;
import no.stelar7.api.r4j.pojo.lor.match.LORMatch;
import no.stelar7.api.r4j.pojo.lor.match.LORParticipant;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRCard;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRDeck;
import no.stelar7.api.r4j.pojo.lor.ranked.LoRPlayerRank;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;

import java.util.*;

public class LoRapiMethods {

    public static APICredentials creds = new APICredentials(EnvReader.ENV_READER.getRiotKey());
    public static R4J r4J = new R4J(creds);

    public static void main(String[] args) {

        CardService service = new CardService();
        LoRDeck deck = LoRDeckCode.decode("CIBQCAIDDIBAGCI33EAQEBADAQDAMAIDBERQCAYDBUAQKCIKAECQGEYCAEBQWLQFAQBQECAKB4JQEAIDBEJQEAIDAQGQ");
        ArrayList<CardAndAmount> cardAndAmountArraylist = new ArrayList<>();

        deck.getDeck()
                .forEach((loRCard, integer) -> cardAndAmountArraylist.add(new CardAndAmount(service.getCardMap().get(loRCard.getCardCode()), integer)));
        cardAndAmountArraylist.sort(Comparator.comparingInt(card -> card.getCard().getCost()));
        cardAndAmountArraylist.forEach(System.out::println);



    }

    //Deck stuff
    public static List<Card> getCardsArrayByDeckCode(String code){
        LoRDeck deck = LoRDeckCode.decode(code);

        ArrayList<Card> cards = new ArrayList<>();
        for (LoRCard card : deck.getDeck().keySet()){
            cards.add(Main.cardService.getCardMap().get(card.getCardCode()));
        }
        return cards;
    }

    public static double getAverageDeckCost(String code){
        LoRDeck deck   = LoRDeckCode.decode(code);
        ArrayList<Card> cards = new ArrayList<>();

        for (LoRCard card : deck.getDeck().keySet()){
            cards.add(Main.cardService.getCardMap().get(card.getCardCode()));
        }
        List<Integer> cost = deck.getDeck().values().stream().toList();

        double sum = 0;
        for (int i = 0; i < cards.size(); i++){
            sum = sum + (cards.get(i).getCost() * cost.get(i));
        }
        return round((sum / 40), 1);
    }

    //Match stuff
    public static List<LoRPlayerRank> getLeaderBoard(RuneterraShard shard){
        return r4J.getLORAPI().getRankedAPI().getLeaderboard(shard);
    }

    public static Optional<LoRDeck> getLatestLorDeckByName(RuneterraShard shard, String name, String tagline){

        String puuid = null;
        try {
            puuid = r4J.getAccountAPI().getAccountByTag(getRegionShard(shard), name, tagline).getPUUID();
        } catch (NullPointerException e) {
            System.out.println("Player PUUID not found");
            return Optional.empty();
        }

        LORMatch outputMatch = null;
        for (String currentMatchString : r4J.getLORAPI().getMatchAPI().getMatchList(shard, puuid)) {
            LORMatch currentMatch = r4J.getLORAPI().getMatchAPI().getMatch(shard, currentMatchString);
            if (currentMatch.getGameType().equalsIgnoreCase("Normal") || currentMatch.getGameType().equalsIgnoreCase("Ranked")){
                outputMatch = currentMatch;
                break;
            }
        }

        for (LORParticipant participant : outputMatch.getPlayers()){
            if (participant.getPuuid().equalsIgnoreCase(puuid)){
                return Optional.of(participant.getDeck());
            }
        }
        return Optional.empty();
    }


    //General Stuff
    public static RegionShard getRegionShard(RuneterraShard runeterraShard){
        return switch (runeterraShard){
            case EUROPE -> RegionShard.EUROPE;
            case APAC -> RegionShard.ASIA;
            case UNKNOWN -> RegionShard.UNKNOWN;
            case AMERICAS -> RegionShard.AMERICAS;
            case ASIA -> RegionShard.ASIA;
            case SEA -> RegionShard.ASIA;
        };
    }

    public static String getDeckEmojiString(String deckCode){
        List<Card> cards = getCardsArrayByDeckCode(deckCode);
        HashSet<Region> hashSet = new HashSet<>();
        cards.forEach(card -> hashSet.addAll(card.getRegions()));


        String output = "";

        for (Region region : hashSet){
            output = output + "\n<:" + region.getAbbreviation() + ":" + region.getEmojiID() + "> **" + region.getRegionFull() + "**";
        }
        return output;
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static Optional<RuneterraShard> getRuneterraShard(String name){
        for (RuneterraShard shard : RuneterraShard.values()){
            if (shard.name().equalsIgnoreCase(name))
                return Optional.of(shard);
        }
        return Optional.empty();
    }

    //Unused
    //
    //
    public static RiotAccount OLDgetAccountByName (RegionShard shard, String name, String tag){
        return r4J.getAccountAPI().getAccountByTag(shard, name, tag);
    }

    public static String OLDgetPUUIDByName (RegionShard shard, String name, String tag){
        return r4J.getAccountAPI().getAccountByTag(shard, name, tag).getPUUID();
    }

    public static LORMatch OLDgetLatestLorMatchByPuuid(RuneterraShard shard, String puuid){

        LORMatch outputMatch = null;
        for (String currentMatchString : r4J.getLORAPI().getMatchAPI().getMatchList(shard, puuid)) {
            LORMatch match = r4J.getLORAPI().getMatchAPI().getMatch(shard, currentMatchString);

            if (match.getGameType().equalsIgnoreCase("Normal") || match.getGameType().equalsIgnoreCase("Ranked")){
                outputMatch = match;
                break;
            }
        }
        return outputMatch;
    }

    public static LORMatch OLDgetLatestLorMatchByName(RuneterraShard shard, String name, String tagline){

        //Todo how to handle regionshard?
        String puuid = r4J.getAccountAPI().getAccountByTag(getRegionShard(shard), name, tagline).getPUUID();

        LORMatch outputMatch = null;

        for (String currentMatchString : r4J.getLORAPI().getMatchAPI().getMatchList(shard, puuid)) {
            LORMatch match = r4J.getLORAPI().getMatchAPI().getMatch(shard, currentMatchString);
            if (match.getGameType().equalsIgnoreCase("Normal") || match.getGameType().equalsIgnoreCase("Ranked")){
                outputMatch = match;
                break;
            }
        }
        return outputMatch;
    }

    public static HashSet<Region> OLDgetRegionsByDeckCode(String deckCode){

        List<Card> cards = getCardsArrayByDeckCode(deckCode);
        HashSet<Region> hashSet = new HashSet<>();
        cards.forEach(card -> hashSet.addAll(card.getRegions()));

        return hashSet;
    }

    public static String OLDgetDeckEmojiStringHorizontal(String deckCode){
        List<Card> cards = getCardsArrayByDeckCode(deckCode);
        HashSet<Region> hashSet = new HashSet<>();
        cards.forEach(card -> hashSet.addAll(card.getRegions()));

        String output = "";

        for (Region region : hashSet){
            output = output + "<:" + region.getAbbreviation() + ":" + region.getEmojiID() + ">";
        }
        return output;
    }
}
