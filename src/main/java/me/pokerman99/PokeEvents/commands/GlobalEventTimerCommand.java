package me.pokerman99.PokeEvents.commands;

import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class GlobalEventTimerCommand implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        int time = args.<Integer>getOne("timer").get();
        Main.globaltimer = time;
        Utils.sendMessage(src, "&aSueccessfully set global event time to " + time);
        return CommandResult.success();
    }
}
