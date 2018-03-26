package me.pokerman99.PokeEvents.commands;

import me.pokerman99.PokeEvents.EventTypes;
import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.Tasks;
import me.pokerman99.PokeEvents.utils.Scoreboards;
import me.pokerman99.PokeEvents.utils.Titles;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class ForceEventCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        EventTypes event = args.<EventTypes>getOne("event type").get();
        switch (event){
            case POKEBATTLE:{
                Main.globaltimer = -1;
                Main.pokebattle = true;
                Main.pokecatch = false;
                Main.winnersname.clear();
                Main.winnersscore.clear();
                Main.event.clear();
                Scoreboards.updateScoreboardPokeBattle();
                Tasks.updateTimerTask();
                Main.eventtype = "battles";
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.sendTitle(Titles.getTitlePokeBattle());
                    online.setScoreboard(Main.scoreboard);
                }
                MessageChannel.TO_CONSOLE.send(Text.of("Started pokebattle event!"));
                break;
            }
            case POKECATCH:{
                Main.globaltimer = -1;
                Main.pokecatch = true;
                Main.pokebattle = false;
                Main.winnersname.clear();
                Main.winnersscore.clear();
                Main.event.clear();
                Scoreboards.updateScoreboardPokeCatch();
                Tasks.updateTimerTask();
                Main.eventtype = "catches";
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.sendTitle(Titles.getTitlePokeCatch());
                    online.setScoreboard(Main.scoreboard);
                }
                MessageChannel.TO_CONSOLE.send(Text.of("Started pokecatch event!"));
                break;
            }
        }
        return CommandResult.success();
    }
}
