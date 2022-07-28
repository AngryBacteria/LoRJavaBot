package bot;
import bot.EventHandling.CommandHandler;
import bot.LoR.Model.SeleniumHelper;
import bot.LoR.json.CardService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.openqa.selenium.Cookie;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Executors;

public class Main{

    public static final String prefix = "$";
    public static final long angryBServer = 838394281059418114L;
    public static final long cnapServer = 244216641737588736L;
    public static final long masteringRuneterra = 790960238496710688L;
    public static final long ownerID = 176323159606165504L;
    public static CardService cardService = new CardService();
    public static final Color mainColor = new Color(0x00B2FF);
    public static final Color errorColor = new Color(0xFF0000);
    public static final Cookie[] cookieList = SeleniumHelper.readCookies();

    public static void main(String[] args) throws LoginException, InterruptedException {
        long startTime = System.nanoTime();

        //Default loading
        JDABuilder builder = JDABuilder.createDefault(EnvReader.ENV_READER.getDiscordKey());
        builder.setActivity(Activity.playing("Legends of Runeterra"));

        //intents and cache
        builder.setEnabledIntents(getNeededIntetns());
        builder.disableCache(Arrays.stream(CacheFlag.values()).toList());
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.EMOTE);
        builder.setMemberCachePolicy(MemberCachePolicy.ONLINE);

        //Executor and build
        builder.setEventPool(Executors.newWorkStealingPool(10), true);
        JDA jda = builder.build();

        //Event Listener
        jda.addEventListener(new CommandHandler());
        jda.awaitReady();

        //Get number of users
        int numberOfUsers = 0;
        for (Guild guid : jda.getGuilds()){
            numberOfUsers = numberOfUsers + guid.getMembers().size();
        }

        long endTime = System.nanoTime();
        long duration = ((endTime - startTime)/1000000);
        System.out.printf("""
                ----------------------- ONLINE -----------------------
                            Bot fully functional and loaded!
                            Startup time: %d milliseconds
                            I am in %s Servers with %s users""", duration, jda.getGuilds().size(), numberOfUsers);
        System.out.println("\n----------------------- Intents -----------------------");
        jda.getGatewayIntents().forEach(System.out::println);
        System.out.println("----------------------- Cache -----------------------");
        jda.getCacheFlags().forEach(System.out::println);

    }

    private static ArrayList<GatewayIntent> getAllIntetns(){
        ArrayList<GatewayIntent> output = new ArrayList<>();
        output.addAll(Arrays.asList(GatewayIntent.values()));
        return output;
    }

    private static ArrayList<GatewayIntent> getNeededIntetns(){
        ArrayList<GatewayIntent> output = new ArrayList<>();

        output.add(GatewayIntent.GUILD_MEMBERS);
        output.add(GatewayIntent.GUILD_PRESENCES);
        output.add(GatewayIntent.GUILD_MESSAGES);
        output.add(GatewayIntent.DIRECT_MESSAGES);
        output.add(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        output.add(GatewayIntent.GUILD_EMOJIS);

        return output;
    }
}
