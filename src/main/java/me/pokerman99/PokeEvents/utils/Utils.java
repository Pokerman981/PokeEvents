package me.pokerman99.PokeEvents.utils;

import me.pokerman99.PokeEvents.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static Main plugin;

    public Utils(Main pluginInstance) {
        plugin = pluginInstance;
    }

    public static final Currency cur = Main.economyService.getDefaultCurrency();

    public static double getPlayerBal(Player player) {
        BigDecimal bal = Main.economyService.getOrCreateAccount(player.getUniqueId()).get().getBalances().get(cur);
        return bal.doubleValue();
    }

    public static String color(String string) {
        return TextSerializers.FORMATTING_CODE.serialize(Text.of(string));
    }

    public static void sendMessage(CommandSource sender, String message) {
        if (sender == null) {
            return;
        }
        sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(color(message)));
    }

    public static Map<String, Integer> getTop() {
        int counter = 1;
        int top = 0;
        int max = 0;
        String topname = null;
        Map<String, Integer> temp = new HashMap<>();
        Map<String, Integer> end = new HashMap<>();

        temp.putAll(Main.event);

        if (Main.event.size() >= 5) {
            max = 5;
        } else {
            max = Main.event.size();
        }

        while (counter <= max) {
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                if (entry.getValue() > top) {
                    top = entry.getValue();
                    topname = entry.getKey();
                }
            }
            //Main.obj.getOrCreateScore(Text.of(topname)).setScore(top);
            temp.remove(topname);
            end.put(topname, top);
            counter++;
            topname = null;
            top = 0;
        }
        return end;
    }

    public static void getWinnersName() {
        int counter = 1;
        int top = 0;
        int max = 0;
        String topname = null;
        Map<String, Integer> temp = new HashMap<>();

        temp.putAll(Main.event);

        if (Main.event.size() >= Main.maxwinners) {
            max = Main.maxwinners;
        } else {
            max = Main.event.size();
        }

        while (counter <= max) {
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                if (entry.getValue() > top) {
                    top = entry.getValue();
                    topname = entry.getKey();
                }
            }
            temp.remove(topname);
            Main.winnersname.add(topname);
            counter++;
            topname = null;
            top = 0;
        }

    }

    public static void getWinnersScore() {
        int counter = 1;
        int top = 0;
        int max = 0;
        String topname = null;
        Map<String, Integer> temp = new HashMap<>();

        temp.putAll(Main.event);

        if (Main.event.size() >= 3) {
            max = 3;
        } else {
            max = Main.event.size();
        }

        while (counter <= max) {
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                if (entry.getValue() > top) {
                    top = entry.getValue();
                    topname = entry.getKey();
                }
            }
            temp.remove(topname);
            Main.winnersscore.add(top);
            counter++;
            topname = null;
            top = 0;
        }

    }

    public static void winnersMessage(){
        //Change to switch
        MessageChannel.TO_ALL.send(Text.of(TextColors.GRAY, TextStyles.STRIKETHROUGH, "-----------------------------------------------------"));
        MessageChannel.TO_ALL.send(Text.of(""));
        MessageChannel.TO_ALL.send(Text.of(TextColors.GREEN, "                            Congratulations To"));
        MessageChannel.TO_ALL.send(Text.of(""));
        switch (Main.winnersname.size()){
            case 1:{
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(0) + " for coming in 1st! (" + Main.winnersscore.get(0) + " %type%)".replace("%type%", Main.eventtype)));
                break;
            }
            case 2:{
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(0) + " for coming in 1st! (" + Main.winnersscore.get(0) + " %type%)".replace("%type%", Main.eventtype)));
                MessageChannel.TO_ALL.send(Text.of(""));
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(1) + " for coming in 2nd! (" + Main.winnersscore.get(1) + " %type%)".replace("%type%", Main.eventtype)));
                break;
            }
            case 3:{
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(0) + " for coming in 1st! (" + Main.winnersscore.get(0) + " %type%)".replace("%type%", Main.eventtype)));
                MessageChannel.TO_ALL.send(Text.of(""));
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(1) + " for coming in 2nd! (" + Main.winnersscore.get(1) + " %type%)".replace("%type%", Main.eventtype)));
                MessageChannel.TO_ALL.send(Text.of(""));
                MessageChannel.TO_ALL.send(Text.of("              " + Main.winnersname.get(2) + " for coming in 3rd! (" + Main.winnersscore.get(2) + " %type%)".replace("%type%", Main.eventtype)));
                break;
            }
        }
        MessageChannel.TO_ALL.send(Text.of(""));
        MessageChannel.TO_ALL.send(Text.of(TextColors.GRAY, TextStyles.STRIKETHROUGH, "-----------------------------------------------------"));
    }

    public static void winnersReward(){
        //Change to switch
        if(Main.pokecatch) {
            switch (Main.winnersname.size()){
                case 1:{
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokecatch.replace("%player%", Main.winnersname.get(0)));
                    break;
                }
                case 2:{
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokecatch.replace("%player%", Main.winnersname.get(0)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward2pokecatch.replace("%player%", Main.winnersname.get(1)));
                    break;
                }
                case 3:{
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokecatch.replace("%player%", Main.winnersname.get(0)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward2pokecatch.replace("%player%", Main.winnersname.get(1)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward3pokecatch.replace("%player%", Main.winnersname.get(2)));
                    break;
                }
            }
        }
        if(Main.pokebattle) {
            switch (Main.winnersname.size()) {
                case 1: {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokebattle.replace("%player%", Main.winnersname.get(0)));
                    break;
                }
                case 2: {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokebattle.replace("%player%", Main.winnersname.get(0)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward2pokebattle.replace("%player%", Main.winnersname.get(1)));
                    break;
                }
                case 3: {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward1pokebattle.replace("%player%", Main.winnersname.get(0)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward2pokebattle.replace("%player%", Main.winnersname.get(1)));
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Main.reward3pokebattle.replace("%player%", Main.winnersname.get(2)));
                    break;
                }
            }
        }
    }

    public static String timeDiffFormat(int timeDiff) {
        int seconds = timeDiff % 60;
        timeDiff = timeDiff / 60;
        int minutes = timeDiff % 60;
        timeDiff = timeDiff / 60;
        int hours = timeDiff % 24;
        timeDiff = timeDiff / 24;
        int days = timeDiff;

        String timeFormat;

        //Formatting
        if (days > 7) {
            timeFormat = days + " days";
        } else if (days > 0) {
            timeFormat = days + "d " + hours + "h";
        } else if (days == 0 && hours > 0) {
            timeFormat = hours + "h " + minutes + "m " + seconds + "s";
        } else if (days == 0 && hours == 0 && minutes > 0) {
            timeFormat = minutes + "m " + seconds + "s";
        } else {
            timeFormat = seconds + "s";
        }

        return timeFormat;
    }
}
