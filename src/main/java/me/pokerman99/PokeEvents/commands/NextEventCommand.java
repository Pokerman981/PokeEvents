package me.pokerman99.PokeEvents.commands;

import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class NextEventCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Utils.sendMessage(src, "&aThe next auto event is in " + Utils.timeDiffFormat((Main.globaltimer + 1) * 60));
        return CommandResult.success();
    }
}
