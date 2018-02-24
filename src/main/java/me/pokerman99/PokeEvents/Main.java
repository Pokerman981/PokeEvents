package me.pokerman99.PokeEvents;

/**
 * Created by troyg on 1/29/2018.
 */

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import me.aust101.PokeVote.VotePartyPlugin;
import me.pokerman99.PokeEvents.commands.test1;
import me.pokerman99.PokeEvents.commands.test2;
import me.pokerman99.PokeEvents.listener.PokeCatchListener;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.economy.EconomyService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Plugin(id = "pokeeventsec",
        name = "PokeEventsEC",
        version = "1.0",
        description = "Plugin for Justin's servers providing pokevents",
        dependencies = {
                @Dependency(id = "voteparty", optional = false)
        })

public class Main {

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path ConfigDir;

    @Inject
    public PluginContainer plugin;
    public PluginContainer getPlugin() {
        return this.plugin;
    }

    public static CommentedConfigurationNode rootNode;

    public static CommentedConfigurationNode config() {
        return rootNode;
    }

    public void save() throws IOException {
        loader.save(rootNode);
    }

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static EconomyService economyService;
    //public static PlaceholderService placeholderapi;


    public Main() {
        super();
    }

    public static VotePartyPlugin voteparty;

    public static Scoreboard scoreboard;
    public static Objective obj;

    public static boolean pokecatch = false;

    public static Map<String, Integer> event = new HashMap<>();

    public static Random rgn = new Random();

    public static int timer = 120;
    public static int scoreboardtimer = 120;

    @Listener
    public void onInit(GameInitializationEvent event){
        Optional<EconomyService> optionalEconomyService = Sponge.getServiceManager().provide(EconomyService.class);
        if (!optionalEconomyService.isPresent())
        {getLogger().severe("There is no Economy Plugin installed on this Server! The money reward will not work!"); return;
        }else{economyService = optionalEconomyService.get();}
        /*Optional<PlaceholderService> papi = Sponge.getServiceManager().provide(PlaceholderService.class);
        if (papi.isPresent()) {
            placeholderapi = papi.get();
        } else {
            // PlaceholderAPI not loaded! Something really screwed up.
        }*/
        try {rootNode = loader.load();} catch (IOException e){e.printStackTrace();}
        rootNode.getNode("config-version").setValue(1.0);
        try {save();} catch (IOException e){e.printStackTrace();}

        CommandSpec scoreboardtest = CommandSpec.builder()
                .executor(new test1())
                //.permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, scoreboardtest, "scoreboardtest");

        CommandSpec votepartytest = CommandSpec.builder()
                .executor(new test2())
                //.permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, votepartytest, "votepartytest");

        Tasks taskSet = new Tasks();

        Pixelmon.EVENT_BUS.register(new PokeCatchListener());
    }


}
