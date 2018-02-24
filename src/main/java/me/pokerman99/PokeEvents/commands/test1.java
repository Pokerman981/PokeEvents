package me.pokerman99.PokeEvents.commands;

import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class test1 implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        for (Player online : Sponge.getServer().getOnlinePlayers()){
            online.sendTitle(Utils.getPokeCatchTitle());
        }
        Utils.updateScoreboard();
        player.setScoreboard(Main.scoreboard);
        Main.pokecatch = true;
        Utils.sendMessage(player, "&aSet scoreboard");
        return CommandResult.success();
    }
}
