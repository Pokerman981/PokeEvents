package me.pokerman99.PokeEvents.listener;

import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.utils.Utils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class CommandListener {

    @Listener
    public void onCommandListener(SendCommandEvent event, @Root CommandSource sender){
        if (Main.commandtoggle){
            if (!event.getCommand().equals("warp")) return;
            if (Main.protectedwarps.contains(event.getArguments())){
                event.setCancelled(true);
                Utils.sendMessage(sender, "&cThis warp is disabled while the auto event is active!");
                return;
            }
        }
    }

}
