package bot.EventHandling;

import bot.LoR.EmbedCreator.BasicEmbeds;
import bot.LoR.Model.HelperMethods;
import bot.LoR.api.LoRapiMethods;
import bot.Main;
import bot.LoR.json.CardIndexing;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ChatCommandHandler {

    public void handleChatEvent(MessageReceivedEvent event){

        Message msg = event.getMessage();
        String messageString = msg.getContentRaw().toLowerCase(Locale.ROOT);

        switch (messageString){
            case ""+ Main.prefix+"purgeslash" -> purgeslash(event);
            case ""+Main.prefix+"addslash" -> addslash(event);
            case ""+Main.prefix+"test" -> testIfWorks(event);
            case ""+Main.prefix+"purgeslashbeta" -> purgeSlashBeta(event);
            case ""+Main.prefix+"addslashbeta" -> addslashBeta(event);
            case ""+Main.prefix+"purgeslashmr" -> purgeSlashMR(event);
            case ""+Main.prefix+"addslashmr" -> addslashMR(event);
        }
    }

    //Commands
    public void purgeslash(MessageReceivedEvent event){

        if (HelperMethods.isOwner(event.getMember().getIdLong())) {
            event.getJDA().getGuildById(Main.angryBServer).updateCommands().addCommands().queue();
            event.getJDA().getGuildById(Main.cnapServer).updateCommands().addCommands().queue();
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    public void addslash(MessageReceivedEvent event){
        if (HelperMethods.isOwner(event.getMember().getIdLong())) {

            event.getJDA().updateCommands().addCommands(getCommandDataArray()).queue();
            event.getJDA().getGuildById(Main.angryBServer).updateCommands().addCommands(getCommandDataArray()).queue();
            event.getJDA().getGuildById(Main.cnapServer).updateCommands().addCommands(getCommandDataArray()).queue();
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    public void addslashBeta(MessageReceivedEvent event){
        if (HelperMethods.isOwner(event.getMember().getIdLong())) {

            addCommandsToServerUpsert(event, Main.angryBServer);
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    public void purgeSlashBeta(MessageReceivedEvent event){
        if (HelperMethods.isOwner(event.getMember().getIdLong())) {
            event.getJDA().getGuildById(Main.angryBServer).updateCommands().addCommands().queue();
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }


    public void addslashMR(MessageReceivedEvent event){
        if (HelperMethods.isOwner(event.getMember().getIdLong())) {
            event.getJDA().getGuildById(Main.masteringRuneterra).updateCommands().addCommands(getMRCommandDataArray()).queue();
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    public void purgeSlashMR(MessageReceivedEvent event){
        if (HelperMethods.isOwner(event.getMember().getIdLong())) {
            event.getJDA().updateCommands().addCommands().queue();
            event.getJDA().getGuildById(Main.masteringRuneterra).updateCommands().addCommands().queue();
            event.getChannel().sendMessage("done").queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    public void testIfWorks(MessageReceivedEvent event){

        if (HelperMethods.isOwner(event.getMember().getIdLong())) {
            event.getChannel().sendMessage("Yes").queue();
            String emojis = LoRapiMethods.getDeckEmojiString("CEDAEAIBCYPQCBABBICAIBZGGY3WOAIDAEBACBIBAQAQKCVBAEBAGBAHCQ5V2AIBAECQEAIEA6FACAICAEBA");
            event.getChannel().sendMessage(emojis).queue();
        }
        else{
            EmbedBuilder embed = BasicEmbeds.buildNoPermissionErrorEmbed(event);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    private ArrayList<CommandData> getCommandDataArray(){

        ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

        commandDataArrayList.add(new CommandDataImpl("info", "Info about the bot"));
        commandDataArrayList.add(new CommandDataImpl("ping", "Ping to bot"));
        commandDataArrayList.add(new CommandDataImpl("card", "Search for LoR cards").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(new CommandDataImpl("cardart", "Search for LoR card-Art").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(new CommandDataImpl("region", "Search for a Runeterra Region. Name and Abbreviation search is supported").addOption(OptionType.STRING, "region", "Runeterra Region name", true, true));
        commandDataArrayList.add(new CommandDataImpl("leaderboard", "Display the current LoR leaderboard by Region (needed)").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.INTEGER, "amount", "amount of Players displayed (max 50)", false).addOption(OptionType.INTEGER, "start", "Rank to start displaying", false));
        commandDataArrayList.add(new CommandDataImpl("latestdeck", "Display the latest deck of a LoR player").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "Name of the player", true).addOption(OptionType.STRING, "tagline", "Tagline without the #", true));
        commandDataArrayList.add(new CommandDataImpl("rank", "Display info about a masters rank player").addOption(OptionType.STRING, "region", "Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "name of the player without the tagline", true));
        commandDataArrayList.add(new CommandDataImpl("deck", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));
        commandDataArrayList.add(new CommandDataImpl("meta", "Display the current LoR Meta Stats").addOption(OptionType.INTEGER, "amount", "amount of Decks displayed", false));
        commandDataArrayList.add(new CommandDataImpl("decklist", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));

        return commandDataArrayList;
    }

    private ArrayList<CommandData> getMRCommandDataArray(){

        ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

        commandDataArrayList.add(new CommandDataImpl("info", "Info about the bot"));
        commandDataArrayList.add(new CommandDataImpl("ping", "Ping to bot"));
        commandDataArrayList.add(new CommandDataImpl("card", "Search for LoR cards").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(new CommandDataImpl("cardart", "Search for LoR card-Art").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(new CommandDataImpl("region", "Search for a Runeterra Region. Name and Abbreviation search is supported").addOption(OptionType.STRING, "region", "Runeterra Region name", true, true));
        commandDataArrayList.add(new CommandDataImpl("leaderboard", "Display the current LoR leaderboard by Region (needed)").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.INTEGER, "amount", "amount of Players displayed (max 50)", false).addOption(OptionType.INTEGER, "start", "Rank to start displaying", false));
        commandDataArrayList.add(new CommandDataImpl("latestdeck", "Display the latest deck of a LoR player").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "Name of the player", true).addOption(OptionType.STRING, "tagline", "Tagline without the #", true));
        commandDataArrayList.add(new CommandDataImpl("rank", "Display info about a masters rank player").addOption(OptionType.STRING, "region", "Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "name of the player without the tagline", true));
        commandDataArrayList.add(new CommandDataImpl("deck", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));
        commandDataArrayList.add(new CommandDataImpl("meta", "Display the current LoR Meta Stats").addOption(OptionType.INTEGER, "amount", "amount of Decks displayed", false));
        commandDataArrayList.add(new CommandDataImpl("decklist", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));

        return commandDataArrayList;
    }

    private ArrayList<CommandData> getCommandDataArrayNew(){

        ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

        commandDataArrayList.add(Commands.slash("info", "Info about the bot"));
        commandDataArrayList.add(Commands.slash("ping", "Ping to bot"));
        commandDataArrayList.add(Commands.slash("card", "Search for LoR cards").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(Commands.slash("cardart", "Search for LoR card-Art").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false));
        commandDataArrayList.add(Commands.slash("region", "Search for a Runeterra Region. Name and Abbreviation search is supported").addOption(OptionType.STRING, "region", "Runeterra Region name", true, true));
        commandDataArrayList.add(Commands.slash("leaderboard", "Display the current LoR leaderboard by Region (needed)").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.INTEGER, "amount", "amount of Players displayed (max 50)", false).addOption(OptionType.INTEGER, "start", "Rank to start displaying", false));
        commandDataArrayList.add(Commands.slash("latestdeck", "Display the latest deck of a LoR player").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "Name of the player", true).addOption(OptionType.STRING, "tagline", "Tagline without the #", true));
        commandDataArrayList.add(Commands.slash("rank", "Display info about a masters rank player").addOption(OptionType.STRING, "region", "Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "name of the player without the tagline", true));
        commandDataArrayList.add(Commands.slash("deck", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));
        commandDataArrayList.add(Commands.slash("meta", "Display the current LoR Meta Stats").addOption(OptionType.INTEGER, "amount", "amount of Decks displayed", false));
        commandDataArrayList.add(Commands.slash("decklist", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true));

        return commandDataArrayList;
    }

    private void addCommandsToServer(MessageReceivedEvent event, long serverID){

        event.getJDA().getGuildById(serverID).updateCommands().addCommands(
                Commands.slash("info", "Info about the bot"),
                Commands.slash("ping", "Ping to bot"),
                Commands.slash("card", "Search for LoR cards").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false),
                Commands.slash("cardart", "Search for LoR card-Art").addOption(OptionType.STRING, "name", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "False is the default. If set to true only exact matches will return the searched Card. False means only a part of the name is enough", false),
                Commands.slash("region", "Search for a Runeterra Region. Name and Abbreviation search is supported").addOption(OptionType.STRING, "region", "Runeterra Region name", true, true),
                Commands.slash("leaderboard", "Display the current LoR leaderboard by Region (needed)").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.INTEGER, "amount", "amount of Players displayed (max 50)", false).addOption(OptionType.INTEGER, "start", "Rank to start displaying", false),
                Commands.slash("latestdeck", "Display the latest deck of a LoR player").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "Name of the player", true).addOption(OptionType.STRING, "tagline", "Tagline without the #", true),
                Commands.slash("rank", "Display info about a masters rank player").addOption(OptionType.STRING, "region", "Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "name of the player without the tagline", true),
                Commands.slash("deck", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true),
                Commands.slash("meta", "Display the current LoR Meta Stats").addOption(OptionType.INTEGER, "amount", "amount of Decks displayed", false),
                Commands.slash("decklist", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true)
        ).queue();
    }

    private void addCommandsToServerUpsert(MessageReceivedEvent event, long serverID){

        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("info", "Info about the bot")).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("ping", "Ping to bot")).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("card", "Search for LoR cards").addOption(OptionType.STRING, "cardname", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "If set to true only exact matches will return the searched Card", false)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("cardart", "Search for LoR card-Art").addOption(OptionType.STRING, "cardname", "Card Name", true).addOption(OptionType.BOOLEAN, "exactmatch", "If set to true only exact matches will return the searched Card", false)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("region", "Search for a Runeterra Region. Name and Abbreviation search is supported").addOption(OptionType.STRING, "region", "Runeterra Region name", true, true)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("leaderboard", "Display the current LoR leaderboard by Region (needed)").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.INTEGER, "amount", "amount of Players displayed (max 50)", false).addOption(OptionType.INTEGER, "start", "Rank to start displaying", false)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("latestdeck", "Display the latest deck of a LoR player").addOption(OptionType.STRING, "region", "Legends of Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "Name of the player", true).addOption(OptionType.STRING, "tagline", "Tagline without the #", true)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("rank", "Display info about a masters rank player").addOption(OptionType.STRING, "region", "Runeterra Region (Europe, Americas, Apac", true, true).addOption(OptionType.STRING, "name", "name of the player without the tagline", true)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("deck", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("meta", "Display the current LoR Meta Stats").addOption(OptionType.INTEGER, "amount", "amount of Decks displayed", false)).queue();
        event.getJDA().getGuildById(serverID).upsertCommand(Commands.slash("decklist", "Display a LoR Deck").addOption(OptionType.STRING, "deckcode", "Valid LoR DeckCode", true)).queue();


    }
}
