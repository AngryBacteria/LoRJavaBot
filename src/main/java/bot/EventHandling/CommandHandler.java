package bot.EventHandling;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandHandler extends ListenerAdapter {

    private final SlashCommandHandler slashCommandHandler = new SlashCommandHandler();
    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final ChatCommandHandler chatCommandHandler = new ChatCommandHandler();
    private final AutocompleteHandler autocompleteHandler = new AutocompleteHandler();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        chatCommandHandler.handleChatEvent(event);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        buttonHandler.handleButtonEvent(event);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashCommandHandler.handleSlashCommand(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        autocompleteHandler.handleAutocomplete(event);
    }
}
