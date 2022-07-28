package bot.LoR.EmbedCreator;

import bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class BasicEmbeds {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static EmbedBuilder buildNoPermissionErrorEmbed(MessageReceivedEvent event){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.errorColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setAuthor(event.getMember().getEffectiveName(), "https://discordapp.com", event.getMember().getEffectiveAvatarUrl());

        embed.setTitle("You have no permission to execute this command!");
        return embed;
    }

    public static EmbedBuilder leaderboardEmbed(SlashCommandInteractionEvent event, String message){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setAuthor(event.getMember().getEffectiveName(), "https://discordapp.com", event.getMember().getEffectiveAvatarUrl());

        embed.setTitle("Leaderboard");
        embed.setDescription(message);
        return embed;
    }

    public static EmbedBuilder regionNotFoundError(SlashCommandInteractionEvent event, String region){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.errorColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setAuthor(event.getMember().getEffectiveName(), "https://discordapp.com", event.getMember().getEffectiveAvatarUrl());

        embed.setTitle("Region `"+ region + "` not found");
        return embed;
    }

    public static EmbedBuilder userInfoEmbed(SlashCommandInteractionEvent event){
        Member member = event.getOption("user").getAsMember();

        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setTitle("Some information about the user: " + member.getEffectiveName());

        embed.addField("", String.format("`Avatar URL:` %s \n`Joined:`  %s\nServer booster since ` %s ` days\n`Discord ID:` %d"
                , member.getEffectiveAvatarUrl(), member.getTimeJoined().format(fmt), member.getTimeBoosted(), member.getIdLong()), false);

        return embed;
    }

    public static EmbedBuilder searchingEmbed (String searchObject){

        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setTitle("Searching " + searchObject + "....");
        return embed;
    }

    public static EmbedBuilder somethingWentWrongEmbed (String reason){

        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.errorColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setTitle("Something went wrong");
        embed.setDescription(reason);
        return embed;
    }

    public static EmbedBuilder getAboutEmbed (){

        String description = """
                I am a LoR companion bot. My Work is giving the users an easy to use bot with useful features all around the CardGame LegendsOfRuneterra.
                I use slash command, if you want to run a command just start typing `/` and you will see what I can do!
                
                __Commands that everyone can see__
                `deck` Give me a LoR DeckCode and I will display an image of the Deck with some additional Info
                `decklist` Give me a LoR DeckCode and I will display a message of the Deck with some additional Info
                `Card` Give me a LoR CardName and I will display info about the Card. You can search for only the card (Set exact match to true) or cards with a similar name
                `CardArt` Same like the the Card command, but I will instead display the full Card-Art
                
                __Commands that only you can see__
                `Meta` Display the current best LoR Archetypes (Last 3 days). You can set the amount of Archetypes to display with the amount field (default is 10)
                `leaderboard` Display the current best top 50 LoR players from the specified region. (Americas, Europe or Apac)
                `rank` Display the rank of a Masters player from the specified region. (Americas, Europe or Apac)
                `region` Display some Lore background to the different Regions in Runeterra
                
                __In beta__
                `Latestdeck` Display the latest deck of a Player. You need to pass in the username, the region (Americas, Europe or Apac) and the TagLine
                
                Created by `AngryBacteria#6666`
                Pm me if you want any additional info, bugs fixed or feature requests. And if you want the bot on your Server :)""";

        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setTitle("LoRBot");
        embed.appendDescription(description);
        return embed;
    }
}
