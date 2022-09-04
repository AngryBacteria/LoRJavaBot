package bot.EventHandling;

import bot.LoR.EmbedCreator.BasicEmbeds;
import bot.LoR.Model.SeleniumHelper;
import bot.LoR.api.LoRapiMethods;
import bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButtonHandler{

    ExecutorService executor = Executors.newCachedThreadPool();

    public void handleButtonEvent(ButtonInteractionEvent event) {

        String buttonID = event.getComponentId();
        switch (buttonID) {
            case "hello" -> this.hello(event);
            case "page_2" -> this.page2(event);
            case "page_1" -> this.page1(event);
        }
    }

    public void hello(ButtonInteractionEvent event){
        event.editMessage("YAY").queue();
        event.getMember().getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage("Hello :]").queue();
        });
    }

    public void page2(ButtonInteractionEvent event){
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Pagination");
        msg.setDescription("Hello World! This is the second page");
        msg.setFooter("Page 2/4");
        msg.setColor(0x33cc33);
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.primary("page_1", Emoji.fromUnicode("◀")));
        buttons.add(Button.danger("page_cancel", Emoji.fromUnicode("❌")));
        buttons.add(Button.primary("page_3", Emoji.fromUnicode("▶")));

        event.editMessageEmbeds(msg.build()).setActionRow(buttons).queue();
        msg.clear();
    }

    public void page1(ButtonInteractionEvent event){
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Pagination");
        msg.setDescription("Hello World! This is the first page");
        msg.setFooter("Page 1/4");
        msg.setColor(0x33cc33);
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.primary("page_1", Emoji.fromUnicode("◀")));
        buttons.add(Button.danger("page_cancel", Emoji.fromUnicode("❌")));
        buttons.add(Button.primary("page_2", Emoji.fromUnicode("▶")));

        event.editMessageEmbeds(msg.build()).setActionRow(buttons).queue();
        msg.clear();
    }

    public void reloadPicture2 (ButtonInteractionEvent event){

        String deckCode = event.getButton().getId();
        event.getHook().deleteOriginal().queue();
        event.deferEdit().queue();
        EmbedBuilder msg = new EmbedBuilder();

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
                    ArrayList<Button> buttons = new ArrayList<>();
                    buttons.add(Button.danger(deckCode, "Reload"));
                    buttons.add(Button.link("https://masteringruneterra.com/deck/" + deckCode, "MasteringRuneterra"));
                    try {
                        file = new File(SeleniumHelper.getPictureFromURL4(deckCode));

                        event.getChannel().sendMessageEmbeds(msg.build())
                                .setActionRow(buttons)
                                .addFile(file, deckCode+".png")
                                .queue();

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Something went wrong when executing the command");
                        event.getHook().editOriginalEmbeds(BasicEmbeds.somethingWentWrongEmbed("Deck Code `" + deckCode + "` not found").build()).queue();
                    }
                });
    }
}
