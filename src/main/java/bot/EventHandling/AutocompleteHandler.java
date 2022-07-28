package bot.EventHandling;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutocompleteHandler {

    private final String[] regions = new String[]{"europe", "americas", "apac"};
    private final String[] regionLore = new String[]{"targon", "noxus", "demacia", "freljord", "shadow isles", "ionia", "shurima", "bilgewater", "piltover & zaun", "bandle city", "ixtal", "void"};
    private final Integer[] leaderBoardSize = new Integer[]{3, 5, 10, 25, 50};


    public void handleAutocomplete(CommandAutoCompleteInteractionEvent event){

        String eventName = event.getName();
        switch (eventName){
            case "leaderboard", "rank", "latestdeck" -> genericSuggestion(event, "region", regions);
            case "region" -> genericSuggestion(event, "region", regionLore);
        }
    }

    public void genericSuggestion(CommandAutoCompleteInteractionEvent event, String name, String[] wordList){
        if (event.getFocusedOption().getName().equals(name)){
            List<Command.Choice> options = Stream.of(wordList)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue().toLowerCase(Locale.ROOT))) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
