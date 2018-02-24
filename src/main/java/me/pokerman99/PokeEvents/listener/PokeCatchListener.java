package me.pokerman99.PokeEvents.listener;


import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import java.util.Map;

public class PokeCatchListener {

    @SubscribeEvent
    public void onPokeCatch(CaptureEvent.SuccessfulCapture event) {
        if (Main.pokecatch) {
            Player player = (Player) event.player;
            Objective obj = Main.obj;
            Scoreboard scoreboard = player.getScoreboard();
            if (Main.event.containsKey(player.getName())) {
                int score = Main.event.get(player.getName()) + 1;
                Main.event.put(player.getName(), score);
            } else {
                Main.event.put(player.getName(), 1);
            }
            Utils.updateScoreboard();
            for (Map.Entry<String, Integer> entry : Utils.getTop().entrySet()) {
                Main.obj.getOrCreateScore(Text.of(entry.getKey())).setScore(entry.getValue());
            }

            Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
            for (Player online : Sponge.getServer().getOnlinePlayers()) {
                online.setScoreboard(Main.scoreboard);
            }


        }
    }

}
