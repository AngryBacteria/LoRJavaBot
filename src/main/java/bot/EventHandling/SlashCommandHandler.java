package bot.EventHandling;

import bot.EnvReader;
import bot.LoR.EmbedCreator.BasicEmbeds;
import bot.LoR.EmbedCreator.LoREmbeds;
import bot.LoR.Model.HelperMethods;
import bot.LoR.Model.SeleniumHelper;
import bot.LoR.api.LoRapiMethods;
import bot.LoR.json.Card;
import bot.LoR.json.subclasses.ArcheTypeStatMatch;
import bot.LoR.json.subclasses.DeckCodeStat;
import bot.LoR.json.subclasses.Region;
import bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import no.stelar7.api.r4j.basic.constants.api.regions.RuneterraShard;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRDeck;
import no.stelar7.api.r4j.pojo.lor.ranked.LoRPlayerRank;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SlashCommandHandler {

    ExecutorService executor = Executors.newCachedThreadPool();

    public void handleSlashCommand(SlashCommandInteractionEvent event) {

        //Commands switch statement to choose the command needed
        String eventName = event.getName();
        switch (eventName.toLowerCase(Locale.ROOT)){
            case "ping" -> this.ping(event);
            case "info" -> this.info(event);
            case "card" -> this.card(event);
            case "cardart" -> this.cardArt(event);
            case "region" -> this.region(event);
            case "leaderboard" -> this.leaderboard(event);
            case "latestdeck" -> this.latestdeck(event);
            case "rank" -> this.getRank(event);
            case "deck" -> this.deckPicture(event);
            case "meta" -> this.meta(event);
            case "decklist" -> this.deckList(event);
            case "decks" -> this.metadecks(event);
            default -> this.somethingWentWrong(event);
        }
    }

    //Command handlers
    public void ping(SlashCommandInteractionEvent event){
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                ).queue(); // Queue both reply and edit
    }

    public void info(SlashCommandInteractionEvent event){
        event.deferReply().setEphemeral(true).queue();
        event.getHook().editOriginalEmbeds(BasicEmbeds.getAboutEmbed().build()).queue();
    }

    public void somethingWentWrong(SlashCommandInteractionEvent event){
        event.reply("Something went wrong").setEphemeral(true).queue();
    }

    public void card(SlashCommandInteractionEvent event){

        event.deferReply().queue();
        boolean exactMatch = false;
        if (event.getOption("exactmatch") != null){
            exactMatch = event.getOption("exactmatch").getAsBoolean();
        }

        String regex;
        if (exactMatch)
            regex = "^(?i)" + event.getOption("cardname").getAsString() + "$";
        else
            regex = "^(?i).*" + event.getOption("cardname").getAsString() + ".*$";

        List<Card> cards = Main.cardService.getCardByNameRegex(regex);
        System.out.println(cards.size());
        System.out.println(cards);
        if (cards.size() == 0){
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("No cards found with name `" + event.getOption("cardname").getAsString() + "`").build()).queue();
        }
        else if (cards.size() < 5){
            for (Card card : cards){
                EmbedBuilder embed = LoREmbeds.cardEmbed(event, card);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
            event.getHook().deleteOriginal().queue();
        }
        else {
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Your search resulted in over 5 cards which is too much to send").build()).queue();
        }
    }

    public void cardArt(SlashCommandInteractionEvent event){

        event.deferReply().queue();
        boolean exactMatch = false;
        if (event.getOption("exactmatch") != null){
            exactMatch = event.getOption("exactmatch").getAsBoolean();
        }

        String regex;
        if (exactMatch)
            regex = "^(?i)" + event.getOption("cardname").getAsString() + "$";
        else
            regex = "^(?i).*" + event.getOption("cardname").getAsString() + ".*$";

        List<Card> cards = Main.cardService.getCardByNameRegex(regex);
        if (cards.size() == 0){
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("No cards found with name `" + event.getOption("cardname").getAsString() + "`").build()).queue();
        }
        else if (cards.size() < 5){
            for (Card card : cards){
                EmbedBuilder embed = LoREmbeds.cardArtEmbed(event, card);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
            event.getHook().deleteOriginal().queue();
        }
        else {
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Your search resulted in over 5 cards which is too much to send").build()).queue();
        }
    }

    public void region(SlashCommandInteractionEvent event){

        event.deferReply().setEphemeral(true).queue();
        if(Main.cardService.getRegionByName(event.getOption("region").getAsString().trim()).isEmpty()){
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("No Region found with name `" + event.getOption("region").getAsString() + "`").build()).queue();
            return;
        }
        Region cardRegion = Main.cardService.getRegionByName(event.getOption("region").getAsString().trim()).get();
        System.out.println(cardRegion);
        EmbedBuilder embed = LoREmbeds.regionEmbed(event, cardRegion);
        event.getHook().editOriginalEmbeds(embed.build()).queue();
        embed.clear();
    }

    public void leaderboard(SlashCommandInteractionEvent event){

        event.deferReply().setEphemeral(true).queue();
        Optional<RuneterraShard> result = LoRapiMethods.getRuneterraShard(event.getOption("region").getAsString().trim());
        int amount = event.getOption("amount", 20, OptionMapping::getAsInt);
        int skipAmount = event.getOption("start", 0, OptionMapping::getAsInt);
        if (amount < 1 || amount > 50)
            amount = 20;
        if (skipAmount < 1)
            skipAmount = 0;

        if (result.isPresent()){

            String output = LoRapiMethods.getLeaderBoard(result.get()).stream()
                    .map(loRPlayerRank -> String.format("\n`%d` %s (%d LP)",(loRPlayerRank.getRank()+1), loRPlayerRank.getName(), loRPlayerRank.getLP()))
                    .skip(skipAmount)
                    .limit(amount)
                    .collect(Collectors.joining());

            EmbedBuilder embed = BasicEmbeds.leaderboardEmbed(event, output);
            event.getHook().editOriginalEmbeds(embed.build()).queue();
            embed.clear();
        }
        else {
            String region = event.getOption("region").getAsString();
            event.getHook().editOriginalEmbeds(BasicEmbeds.regionNotFoundError(event, region).build()).queue();
        }
    }

    public void latestdeck(SlashCommandInteractionEvent event){

        event.deferReply().setEphemeral(true).queue();
        //Get rShard
        Optional<RuneterraShard> regionResult = LoRapiMethods.getRuneterraShard(event.getOption("region").getAsString().trim());

        if (regionResult.isEmpty()){
            String region = event.getOption("region").getAsString();
            event.getHook().editOriginalEmbeds(BasicEmbeds.regionNotFoundError(event, region).build()).queue();
            return;
        }

        //Get user input
        RuneterraShard regionShard = regionResult.get();
        String name = event.getOption("name").getAsString().trim();
        String tagline = event.getOption("tagline").getAsString().trim();

        //try to get deck info with error catching
        Optional<LoRDeck> loRDeckSearchResult;
        try {
            loRDeckSearchResult = LoRapiMethods.getLatestLorDeckByName(regionShard, name, tagline);
        } catch (Exception e) {
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Something went wrong when searching the user").build()).queue();
            return;
        }

        //Get deckcode with error catching
        if (loRDeckSearchResult.isPresent()) {
            //event.getHook().editOriginal("https://masteringruneterra.com/deck/" + loRDeckSearchResult.get().getDeckCode()).queue();

            String deckCode = loRDeckSearchResult.get().getDeckCode();
            EmbedBuilder msg = new EmbedBuilder();

            try {
                msg.setDescription(LoRapiMethods.getDeckEmojiString(deckCode));
            } catch (Exception e) {
                msg.setDescription("");
            }

            msg.setColor(Main.mainColor);
            msg.setImage("attachment://" + deckCode + ".png");

            this.executor.execute(
                    () -> {
                        File file = null;
                        try {
                            file = new File(SeleniumHelper.getPictureFromURL4(deckCode));
                            event.getHook().editOriginalEmbeds(msg.build())
                                    .setActionRow(Button.link("https://masteringruneterra.com/deck/" + deckCode, "MasteringRuneterra"))
                                    .addFile(file, deckCode+".png")
                                    .queue();

                        } catch (Exception e) {
                            System.out.println(e.toString());
                            System.out.println("Something went wrong when executing the command");
                            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Deck Code `" + deckCode + "` not found").build()).queue();
                        }
                    });
        }
        else
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Something went wrong when searching the user").build()).queue();

    }


    public void getRank(SlashCommandInteractionEvent event){

        event.deferReply().setEphemeral(true).queue();
        Optional<RuneterraShard> result = LoRapiMethods.getRuneterraShard(event.getOption("region").getAsString().trim());
        if (result.isPresent()){

            String playerName = event.getOption("name").getAsString().trim();

            for (LoRPlayerRank loRPlayerRank : LoRapiMethods.getLeaderBoard(result.get())){
                if (loRPlayerRank.getName().equalsIgnoreCase(playerName)){

                    EmbedBuilder msg = new EmbedBuilder();
                    msg.setTitle(loRPlayerRank.getName());
                    msg.appendDescription("`LP:` " + loRPlayerRank.getLP());
                    msg.appendDescription("\n`Masters Rank:` " + (loRPlayerRank.getRank()+1));
                    msg.setColor(Main.mainColor);
                    event.getHook().editOriginalEmbeds(msg.build()).queue();
                    return;
                }
            }
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Player not found").build()).queue();
        }

        else {
            String region = event.getOption("region").getAsString();
            event.getHook().editOriginalEmbeds(BasicEmbeds.regionNotFoundError(event, region).build()).queue();
        }
    }

    public void deckPicture(SlashCommandInteractionEvent event){

        event.deferReply().queue();
        String deckCode = StringUtils.strip(event.getOption("deckcode", "noInput", OptionMapping::getAsString), "/").trim();
        EmbedBuilder msg = new EmbedBuilder();
        //msg.setTitle("Deck", "https://masteringruneterra.com/deck/" + deckCode);

        try {
            msg.setDescription(LoRapiMethods.getDeckEmojiString(deckCode));
        } catch (Exception e) {
            System.out.println(e.toString());
            msg.setDescription("");
        }

        msg.setColor(Main.mainColor);
        msg.setImage("attachment://" + deckCode + ".png");

        this.executor.execute(
                () -> {
            File file = null;
            try {
                file = new File(SeleniumHelper.getPictureFromURL4(deckCode));
                event.getHook().editOriginalEmbeds(msg.build())
                        .setActionRow(Button.link("https://masteringruneterra.com/deck/" + deckCode, "MasteringRuneterra"))
                        .addFile(file, deckCode+".png")
                        .queue();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Something went wrong when executing the command");
                event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Deck Code `" + deckCode + "` not found").build()).queue();
            }
        });
    }

    public void meta(SlashCommandInteractionEvent event){

        event.deferReply().setEphemeral(true).queue();
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("LoR Current Meta (Last 3 Days)", "https://lor-meta.com/tierlist/");
        msg.setFooter("Data from: https://lor-meta.com/");
        msg.setColor(Main.mainColor);

        int amount = event.getOption("amount", 10, OptionMapping::getAsInt);

        if (amount < 1 || amount > 40)
            amount = 10;

        String description = HelperMethods.parseMatchesCSV(EnvReader.ENV_READER.getBasePath() + "csv/3days.csv").stream()
                .skip(1)
                .limit(amount)
                .map(ArcheTypeStatMatch::toString)
                .collect(Collectors.joining());

        msg.setDescription(description);
        event.getHook().editOriginalEmbeds(msg.build())
                .setActionRow(Button.link("https://masteringruneterra.com/meta-tier-list/", "MasteringRuneterra"))
                .queue();
    }

    public void deckList(SlashCommandInteractionEvent event){
        event.deferReply().queue();
        String deckCode = StringUtils.strip(event.getOption("deckcode", "noInput", OptionMapping::getAsString), "/").trim();

        try {
            event.getHook().editOriginalEmbeds(LoREmbeds.deckListEmbed(deckCode).build())
                    .setActionRow(Button.link("https://masteringruneterra.com/deck/" + deckCode, "MasteringRuneterra")).queue();
        } catch (Exception e) {
            event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("`Deck Code not found`").build()).queue();
            System.out.println(e.toString());
        }
    }

    public void metadecks(SlashCommandInteractionEvent event){
        event.deferReply().queue();

        int amount = event.getOption("amount", 20, OptionMapping::getAsInt);
        int skipAmount = event.getOption("start", 0, OptionMapping::getAsInt);
        if (amount < 1 || amount > 50)
            amount = 20;
        if (skipAmount < 1)
            skipAmount = 0;

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Most played Decks in Masters (Last 3 Days)", "https://lor-meta.com/tierlist/");
        msg.setFooter("Data from: https://lor-meta.com/");
        msg.setColor(Main.mainColor);

        String description = HelperMethods.parseDeckStatCSV("C:\\Users\\nijog\\Downloads\\codes3.csv").stream()
                .skip(1+skipAmount)
                .limit(amount)
                .map(DeckCodeStat::toString)
                .collect(Collectors.joining());

        msg.setDescription(description);
        event.getHook().editOriginalEmbeds(msg.build())
                .setActionRow(Button.link("https://masteringruneterra.com/meta-tier-list/", "MasteringRuneterra"))
                .queue();
    }
    }
