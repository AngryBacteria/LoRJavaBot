package bot.EventHandling;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonHandler{

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
}
