package me.pokerman99.PokeEvents.listener;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.PlayerBattleEndedEvent;
import me.pokerman99.PokeEvents.Main;
import me.pokerman99.PokeEvents.utils.Scoreboards;
import me.pokerman99.PokeEvents.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Map;

public class PokeBattleListener {

    @SubscribeEvent
    public void onPokeBattle(PlayerBattleEndedEvent event) {
        if (Main.pokebattle) {
            if (!(event.battleController.playerNumber == 1)) return;
            if (!(event.result.toString() == "VICTORY")) return;
            Player player = (Player) event.player;
            if (Main.event.containsKey(player.getName())) {
                int score = Main.event.get(player.getName()) + 1;
                Main.event.put(player.getName(), score);
            } else {
                Main.event.put(player.getName(), 1);
            }
            Scoreboards.updateScoreboardPokeBattle();
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
