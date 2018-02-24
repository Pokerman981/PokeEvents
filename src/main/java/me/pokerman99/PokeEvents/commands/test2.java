package me.pokerman99.PokeEvents.commands;

import me.aust101.PokeVote.ScoreboardListener;
import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.TimeUnit;

public class test2 implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        ///try {new ScoreboardListener(Main.voteparty, player);} catch (NullPointerException e){}
        MessageChannel.TO_ALL.send(Text.of("{value}"));
        Utils.sendMessage(player, "&aSet vote party to the scoreboard");
        return CommandResult.success();
    }
}
