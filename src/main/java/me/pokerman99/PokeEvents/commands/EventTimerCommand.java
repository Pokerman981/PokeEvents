package me.pokerman99.PokeEvents.commands;

import me.pokerman99.PokeEvents.EventTypes;
import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.io.IOException;

import static me.pokerman99.PokeEvents.EventTypes.POKEBATTLE;
import static me.pokerman99.PokeEvents.EventTypes.POKECATCH;

public class EventTimerCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        EventTypes event = args.<EventTypes>getOne("event").get();
        int time = args.<Integer>getOne("timer").get();
        if (Main.isRunning){
            Utils.sendMessage(src, "&cThere is currently an event running! Cannot change the event timer!");
            return CommandResult.empty();
        }
        Main.rootNode.getNode("events", event.toString().toLowerCase(), "timer").setValue(time);
        try{Main.instance.loader.save(Main.rootNode);} catch (IOException e){}
        Main.populateVariables();
        Utils.sendMessage(src, "&aSuccessfully changed " + event + "'s timer to " + time);
        return CommandResult.success();
    }
}
