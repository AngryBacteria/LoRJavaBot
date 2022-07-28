package bot.LoR.EmbedCreator;

import bot.LoR.api.LoRapiMethods;
import bot.LoR.json.Card;
import bot.LoR.json.subclasses.CardAndAmount;
import bot.LoR.json.subclasses.Region;
import bot.LoR.json.subclasses.SuperType;
import bot.LoR.json.subclasses.Type;
import bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import no.stelar7.api.r4j.impl.lor.LoRDeckCode;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRDeck;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoREmbeds {

    public static EmbedBuilder cardEmbed(SlashCommandInteractionEvent event, Card card){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setFooter("Artist: " + card.getArtistName());
        embed.setImage(card.getAssets().getGameAbsolutePath());

        embed.setTitle(card.getName(), "https://runeterra.ar/cards/" + card.getCardCode());
        return embed;
    }

    public static EmbedBuilder cardArtEmbed(SlashCommandInteractionEvent event, Card card){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.setFooter("Artist: " + card.getArtistName());
        embed.setImage(card.getAssets().getFullAbsolutePath());

        embed.setTitle(card.getName(), "https://runeterra.ar/cards/" + card.getCardCode());
        embed.setDescription(card.getFlavorText());
        return embed;
    }

    public static EmbedBuilder regionEmbed(SlashCommandInteractionEvent event, Region region){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();

        embed.setColor(Main.mainColor);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setImage(region.getRandomPictureURL());
        embed.setAuthor(event.getMember().getEffectiveName(), region.getLoreTextURL(), event.getMember().getEffectiveAvatarUrl());
        embed.setTitle(region.getRegionFull(), region.getLoreTextURL());
        embed.setDescription(region.getDescription());

        return embed;
    }

    public static EmbedBuilder deckListEmbed(String deckCode){
        EmbedBuilder embed = new EmbedBuilder();
        embed.clear();
        embed.setColor(Main.mainColor);
        embed.appendDescription(LoRapiMethods.getDeckEmojiString(deckCode));


        LoRDeck deck = LoRDeckCode.decode(deckCode);
        ArrayList<CardAndAmount> cardAndAmountArraylist = new ArrayList<>();

        deck.getDeck()
                .forEach((loRCard, integer) -> cardAndAmountArraylist.add(new CardAndAmount(Main.cardService.getCardMap().get(loRCard.getCardCode()), integer)));
        cardAndAmountArraylist.sort(Comparator.comparingInt(card -> card.getCard().getCost()));


        List<String> champions = new ArrayList<>();
        List<String> landmarks = new ArrayList<>();
        List<String> followers = new ArrayList<>();
        List<String> spells = new ArrayList<>();


        for (CardAndAmount cardAndAmount : cardAndAmountArraylist) {

            if (cardAndAmount.getCard().getType().equals(Type.LANDMARK))
                landmarks.add(cardAndAmount.toString());

            if (cardAndAmount.getCard().getType().equals(Type.SPELL))
                spells.add(cardAndAmount.toString());

            if (cardAndAmount.getCard().getType().equals(Type.UNIT) && cardAndAmount.getCard().getSupertype().equals(SuperType.CHAMPION))
                champions.add(cardAndAmount.toString());

            if (cardAndAmount.getCard().getType().equals(Type.UNIT) && cardAndAmount.getCard().getSupertype().equals(SuperType.NONE)) {
                followers.add(cardAndAmount.toString());
            }
        }

        if (!champions.isEmpty())
            embed.addField("<:championMR:968930228091117649> Champions", String.join("\n", champions), true);

        if (!followers.isEmpty())
            embed.addField("<:followerMR:968930227952697375> Followers", String.join("\n", followers), true);

        if (!spells.isEmpty())
            embed.addField("<:spellMR:968930227784917053> Spells", String.join("\n", spells), true);

        if (!landmarks.isEmpty())
            embed.addField("<:landmarkMR:968930227919138817> Landmarks", String.join("\n", landmarks), true);

        return embed;

    }
}
