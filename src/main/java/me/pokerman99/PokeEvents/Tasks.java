package me.pokerman99.PokeEvents;

import me.aust101.PokeVote.ScoreboardListener;
import me.pokerman99.PokeEvents.utils.Scoreboards;
import me.pokerman99.PokeEvents.utils.Titles;
import me.pokerman99.PokeEvents.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Tasks {

    public Tasks() {
        Task.Builder autoeventtimertask = Task.builder().delay(5, TimeUnit.SECONDS).interval(1, TimeUnit.MINUTES).name("autoeventtimertask").execute(new Runnable() {
            @Override
            public void run() {
                if (Main.globaltimer == 0) {
                    randomEventStart();
                    Main.isRunning = true;
                    Main.globaltimer = -1;
                    return;
                }
                Main.globaltimer--;
            }
        });
        autoeventtimertask.submit(Main.getInstance());
    }

    public static void updateTimerTask() {
        Task scoreboardtimertask = Task.builder().interval(1, TimeUnit.SECONDS).name("scoreboardtimertask")
                .execute(new updateTimer()).submit(Main.getInstance());
    }

    private static class updateTimer implements Consumer<Task> {
        @Override
        public void accept(Task task) {
            if (Main.pokecatch) {
                try {Main.obj.removeScore(Text.of(TextColors.RED , "Time Left: ", Main.scoreboardtimerpokecatch + 1));} catch (NullPointerException e) { }
                Main.obj.getOrCreateScore(Text.of(TextColors.RED , "Time Left: ", Main.scoreboardtimerpokecatch)).setScore(-2);
                Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.setScoreboard(Main.scoreboard);
                }

                if (Main.scoreboardtimerpokecatch <= 0) {
                    if (Sponge.getGame().getScheduler().getTaskById(task.getUniqueId()).isPresent()){
                        Sponge.getGame().getScheduler().getTaskById(task.getUniqueId()).get().cancel();
                    }
                    Utils.getWinnersName();
                    Utils.getWinnersScore();

                    if (!Main.winnersname.isEmpty()) {
                        Utils.winnersMessage();
                        Utils.winnersReward();
                    }

                    eventReset();

                    for (Player online : Sponge.getServer().getOnlinePlayers()) {
                        try {
                            new ScoreboardListener(Main.voteparty, online);
                        } catch (NullPointerException e) { }
                    }
                }

                Main.scoreboardtimerpokecatch--;
            }
            if (Main.pokebattle) {
                try {Main.obj.removeScore(Text.of(TextColors.RED , "Time Left: ", Main.scoreboardtimerpokebattle + 1));} catch (NullPointerException e) { }
                Main.obj.getOrCreateScore(Text.of(TextColors.RED , "Time Left: ", Main.scoreboardtimerpokebattle)).setScore(-2);
                Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.setScoreboard(Main.scoreboard);
                }

                if (Main.scoreboardtimerpokebattle <= 0) {
                    if (Sponge.getGame().getScheduler().getTaskById(task.getUniqueId()).isPresent()){
                        Sponge.getGame().getScheduler().getTaskById(task.getUniqueId()).get().cancel();
                    }
                    Utils.getWinnersName();
                    Utils.getWinnersScore();

                    if (!Main.winnersname.isEmpty()) {
                        Utils.winnersMessage();
                        Utils.winnersReward();
                    }

                    eventReset();

                    for (Player online : Sponge.getServer().getOnlinePlayers()) {
                        try {
                            new ScoreboardListener(Main.voteparty, online);
                        } catch (NullPointerException e) { }
                    }
                }

                Main.scoreboardtimerpokebattle--;
            }
        }
    }

    public static void randomEventStart(){
        int event = Main.rgn.nextInt(2) + 1;
        Main.commandtoggle = true;
        Main.winnersname.clear();
        Main.winnersscore.clear();
        Main.event.clear();
        MessageChannel.TO_CONSOLE.send(Text.of(TextColors.AQUA, "====================="));
        switch (event) {
            case 1: {
                Main.pokecatch = true;
                Main.pokebattle = false;
                Scoreboards.updateScoreboardPokeCatch();
                updateTimerTask();
                Main.eventtype = "catches";
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.sendTitle(Titles.getTitlePokeCatch());
                    online.setScoreboard(Main.scoreboard);
                }
                MessageChannel.TO_CONSOLE.send(Text.of("Started pokecatch event!"));
                break;
            }
            case 2: {
                Main.pokebattle = true;
                Main.pokecatch = false;
                Scoreboards.updateScoreboardPokeBattle();
                updateTimerTask();
                Main.eventtype = "battles";
                for (Player online : Sponge.getServer().getOnlinePlayers()) {
                    online.sendTitle(Titles.getTitlePokeBattle());
                    online.setScoreboard(Main.scoreboard);
                }
                MessageChannel.TO_CONSOLE.send(Text.of("Started pokebattle event!"));
                break;
            }
            case 3: {
                MessageChannel.TO_CONSOLE.send(Text.of("No event assigned!"));
                break;
            }
        }
        MessageChannel.TO_CONSOLE.send(Text.of(Sponge.getServer().getOnlinePlayers().size(), " player's online"));
        MessageChannel.TO_CONSOLE.send(Text.of(TextColors.AQUA, "====================="));
    }

    public static void eventReset(){
        MessageChannel.TO_CONSOLE.send(Text.of(TextColors.AQUA, "====================="));
        Main.isRunning = false;
        if (Main.pokecatch){
            Main.pokecatch = false;
            Main.scoreboardtimerpokecatch = Main.rootNode.getNode("events", "pokecatch", "timer").getInt();
            MessageChannel.TO_CONSOLE.send(Text.of("Ended pokecatch event!"));
        }
        if (Main.pokebattle){
            Main.pokebattle = false;
            Main.scoreboardtimerpokebattle = Main.rootNode.getNode("events", "pokebattle", "timer").getInt();
            MessageChannel.TO_CONSOLE.send(Text.of("Ended pokebattle event!"));
        }
        Main.globaltimer = 0;
        Main.globaltimer = Main.rgn.nextInt(60) + Main.timeraddon;
        Main.scoreboard.clearSlot(DisplaySlots.SIDEBAR);
        Main.event.clear();
        Main.winnersname.clear();
        Main.winnersscore.clear();
        Main.eventtype = null;
        Main.commandtoggle = false;
        MessageChannel.TO_CONSOLE.send(Text.of(Sponge.getServer().getOnlinePlayers().size(), " player's online"));
        MessageChannel.TO_CONSOLE.send(Text.of(TextColors.AQUA, "====================="));
    }
}




//MessageChannel.TO_ALL.send(Text.of(TextColors.GRAY, TextStyles.STRIKETHROUGH, "-----------------------------------------------------"));