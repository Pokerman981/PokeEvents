package me.pokerman99.PokeEvents;

/*
 * Created by troyg on 1/29/2018.
 */

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import me.aust101.PokeVote.VotePartyPlugin;
import me.pokerman99.PokeEvents.commands.EventTimerCommand;
import me.pokerman99.PokeEvents.commands.ForceEventCommand;
import me.pokerman99.PokeEvents.commands.GlobalEventTimerCommand;
import me.pokerman99.PokeEvents.commands.NextEventCommand;
import me.pokerman99.PokeEvents.listener.CommandListener;
import me.pokerman99.PokeEvents.listener.PokeBattleListener;
import me.pokerman99.PokeEvents.listener.PokeCatchListener;
import me.rojo8399.placeholderapi.PlaceholderService;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

@Plugin(id = "pokeeventsec",
        name = "PokeEventsEC",
        version = "1.0",
        description = "Plugin for Justin's servers providing pokevents",
        dependencies = {
                @Dependency(id = "voteparty", optional = false),
                @Dependency(id = "placeholderapi", optional = false)
        })
public class Main {
    @Inject @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject @ConfigDir(sharedRoot = false)
    private Path ConfigDir;

    public static CommentedConfigurationNode rootNode;

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static VotePartyPlugin voteparty;
    public static PlaceholderService placeholderService;
    public static EconomyService economyService;
    public static Scoreboard scoreboard;
    public static Objective obj;
    public static Random rgn = new Random();

    public static int timeraddon = 60;
    public static int globaltimer = rgn.nextInt(60) + timeraddon;
    public static List<String> protectedwarps = new ArrayList<>();
    public static Map<String, Integer> event = new HashMap<>();
    public static List<String> winnersname = new ArrayList<>();
    public static List<Integer> winnersscore = new ArrayList<>();
    public static boolean isRunning = false;
    public static boolean commandtoggle = false;
    public static String eventtype;
    public static int maxwinners;

    public static boolean pokecatch = false;
    public static int scoreboardtimerpokecatch;
    public static String reward1pokecatch;
    public static String reward2pokecatch;
    public static String reward3pokecatch;
    public static String rewardinfo1pokecatch;
    public static String rewardinfo2pokecatch;
    public static String rewardinfo3pokecatch;

    public static boolean pokebattle = false;
    public static int scoreboardtimerpokebattle;
    public static String reward1pokebattle;
    public static String reward2pokebattle;
    public static String reward3pokebattle;
    public static String rewardinfo1pokebattle;
    public static String rewardinfo2pokebattle;
    public static String rewardinfo3pokebattle;


    public void loadConfigurationSettings() throws IOException {
        rootNode = loader.load();

        setDefaultConfig();
    }

    public void setDefaultConfig() throws IOException{
        if (!Files.exists(defaultConfig)){
            rootNode.getNode("config-version").setValue(1.0);
        }
        if (rootNode.getNode("events", "pokecatch").isVirtual()) {
            CommentedConfigurationNode pokecatch = rootNode.getNode("events", "pokecatch");
            pokecatch.getNode("timer").setValue(600).setComment("Counts in seconds. 600 = 10 mins");
            pokecatch.getNode("rewards", "reward-1").setValue("pokegive %player% zapdos lvl:5").setComment("Set the reward for the first place winner. %player% = playername. Executes command from console");
            pokecatch.getNode("rewards", "reward-2").setValue("pokegive %player% zapdos lvl:5");
            pokecatch.getNode("rewards", "reward-3").setValue("pokegive %player% zapdos lvl:5");
            pokecatch.getNode("reward-info", "info-1").setValue("Random Shiny").setComment("What the scoreboard says when an event is active for what the rewards are.");
            pokecatch.getNode("reward-info", "info-2").setValue("Random Shiny");
            pokecatch.getNode("reward-info", "info-3").setValue("Random Shiny");
            loader.save(rootNode);
        }

        if (rootNode.getNode("events", "pokebattle").isVirtual()) {
            CommentedConfigurationNode pokebattle = rootNode.getNode("events", "pokebattle");
            pokebattle.getNode("timer").setValue(600).setComment("Counts in seconds. 600 = 10 mins");
            pokebattle.getNode("rewards", "reward-1").setValue("pokegive %player% zapdos lvl:5").setComment("Set the reward for the first place winner. %player% = playername. Executes command from console");
            pokebattle.getNode("rewards", "reward-2").setValue("pokegive %player% zapdos lvl:5");
            pokebattle.getNode("rewards", "reward-3").setValue("pokegive %player% zapdos lvl:5");
            pokebattle.getNode("reward-info", "info-1").setValue("Random Shiny").setComment("What the scoreboard says when an event is active for what the rewards are.");
            pokebattle.getNode("reward-info", "info-2").setValue("Random Shiny");
            pokebattle.getNode("reward-info", "info-3").setValue("Random Shiny");
            loader.save(rootNode);
        }

        if (rootNode.getNode("globaltimer").isVirtual()){
            rootNode.getNode("globaltimer").setValue(60).setComment("randomnumber.nextInt(60) + #. Max time with default is 2 hours. Determins when the events happen");
            loader.save(rootNode);
        }

        if (rootNode.getNode("max-winners").isVirtual()){
            rootNode.getNode("max-winners").setValue(3).setComment("Determins how many winners there can be, max is 3 any higher will error.");
            loader.save(rootNode);
        }

        if(rootNode.getNode("protected-warps").isVirtual()){
            List<String> protectedwarpslist = new ArrayList<>();
            protectedwarpslist.add("ev");
            protectedwarpslist.add("safari");
            CommentedConfigurationNode protectedwarps = rootNode.getNode("protected-warps");
            protectedwarps.setValue(protectedwarpslist).setComment("Warps that wont be active during a poke related event.");
            loader.save(rootNode);
        }
    }

    public static void populateVariables(){
        timeraddon = rootNode.getNode("globaltimer").getInt();
        maxwinners = rootNode.getNode("max-winners").getInt();

        CommentedConfigurationNode pokecatch = rootNode.getNode("events", "pokecatch");
        scoreboardtimerpokecatch = pokecatch.getNode("timer").getInt();
        reward1pokecatch = pokecatch.getNode("rewards", "reward-1").getString();
        reward2pokecatch = pokecatch.getNode("rewards", "reward-2").getString();
        reward3pokecatch = pokecatch.getNode("rewards", "reward-3").getString();
        rewardinfo1pokecatch = pokecatch.getNode("reward-info", "info-1").getString();
        rewardinfo2pokecatch = pokecatch.getNode("reward-info", "info-2").getString();
        rewardinfo3pokecatch = pokecatch.getNode("reward-info", "info-3").getString();

        CommentedConfigurationNode pokebattle = rootNode.getNode("events", "pokebattle");
        scoreboardtimerpokebattle = pokebattle.getNode("timer").getInt();
        reward1pokebattle = pokebattle.getNode("rewards", "reward-1").getString();
        reward2pokebattle = pokebattle.getNode("rewards", "reward-2").getString();
        reward3pokebattle = pokebattle.getNode("rewards", "reward-3").getString();
        rewardinfo1pokebattle = pokebattle.getNode("reward-info", "info-1").getString();
        rewardinfo2pokebattle = pokebattle.getNode("reward-info", "info-2").getString();
        rewardinfo3pokebattle = pokebattle.getNode("reward-info", "info-3").getString();



        try{protectedwarps = rootNode.getNode("protected-warps").getList(TypeToken.of(String.class));} catch (ObjectMappingException ex){}
    }

    @Listener
    public void onInit(GameInitializationEvent event) throws IOException {
        economyService = Sponge.getServiceManager().provide(EconomyService.class).get();
        placeholderService = Sponge.getServiceManager().provide(PlaceholderService.class).get();

        loadConfigurationSettings();
        populateVariables();
        instance = this;
        Tasks taskSet = new Tasks();

        CommandSpec GLobalEventTimerCommand = CommandSpec.builder()
                .executor(new GlobalEventTimerCommand())
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("timer"))))
                .permission("adminshopec.admin")
                .build();

        CommandSpec EventTimerCommand = CommandSpec.builder()
                .executor(new EventTimerCommand())
                .arguments(GenericArguments.onlyOne(GenericArguments.enumValue(Text.of("event"), EventTypes.class)), GenericArguments.onlyOne(GenericArguments.integer(Text.of("timer"))))
                .permission("adminshopec.admin")
                .build();

        CommandSpec NextEventCommand = CommandSpec.builder()
                .executor(new NextEventCommand())
                .permission("pokeeventec.staff")
                .build();

        CommandSpec ForceEventCommand = CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.enumValue(Text.of("event type"), EventTypes.class)))
                .executor(new ForceEventCommand())
                .permission("pokeeventec.admin")
                .build();

        CommandSpec pev = CommandSpec.builder()
                .child(NextEventCommand, "nextevent")
                .child(ForceEventCommand, "force")
                .child(GLobalEventTimerCommand, "settimer")
                .child(EventTimerCommand, "seteventtimer")
                .permission("pokeeventec.staff")
                .build();

        Sponge.getCommandManager().register(this, pev, "pev");
        Pixelmon.EVENT_BUS.register(new PokeCatchListener());
        Pixelmon.EVENT_BUS.register(new PokeBattleListener());
        Sponge.getEventManager().registerListeners(this, new CommandListener());
    }
}