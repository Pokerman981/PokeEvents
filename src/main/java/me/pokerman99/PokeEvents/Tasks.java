package me.pokerman99.PokeEvents;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.TimeUnit;

public class Tasks {

    public Tasks() {
        Task.Builder autoeventtimertask = Task.builder().delay(5, TimeUnit.SECONDS).interval(1, TimeUnit.SECONDS).name("autoeventtimertask").execute(new Runnable() {
            @Override
            public void run() {
                MessageChannel.TO_ALL.send(Text.of("1"));
                if (Main.timer == 0) {
                    int event = 1;
                    switch (event) {
                        case 1: {
                            Main.pokecatch = true;
                            Utils.updateScoreboard();
                            updateTimer();
                            for (Player online : Sponge.getServer().getOnlinePlayers()) {
                                online.sendTitle(Utils.getPokeCatchTitle());
                                online.setScoreboard(Main.scoreboard);
                            }
                            Main.timer = Main.rgn.nextInt(120) * 60;
                            MessageChannel.TO_ALL.send(Text.of(Main.timer));
                            break;
                        }
                        case 2: {
                            break;
                        }
                        case 3: {
                            break;
                        }
                    }
                }
                Main.timer--;
            }
        });
        autoeventtimertask.submit(Main.getInstance());
    }

    public static void updateTimer() {
        Task.Builder scoreboardtimertask = Task.builder().interval(1, TimeUnit.SECONDS).name("scoreboardtimertask");
        scoreboardtimertask.execute(task -> {
            try {
                Main.obj.removeScore(Text.of(TextColors.LIGHT_PURPLE, "Time Left: ", Main.scoreboardtimer + 1));
            } catch (NullPointerException e) {
            }
            Main.obj.getOrCreateScore(Text.of(TextColors.LIGHT_PURPLE, "Time Left: ", Main.scoreboardtimer)).setScore(-8);
            Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
            for (Player online : Sponge.getServer().getOnlinePlayers()) {
                online.setScoreboard(Main.scoreboard);
            }
            if (Main.scoreboardtimer == 0) {
                scoreboardtimertask.submit(Main.getInstance()).cancel();
            }
            Main.scoreboardtimer--;
        });
        scoreboardtimertask.submit(Main.getInstance());
    }
}