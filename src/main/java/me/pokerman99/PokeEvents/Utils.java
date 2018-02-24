package me.pokerman99.PokeEvents;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;

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

    public static Title getPokeCatchTitle() {

        Title title = Title.builder()
                .title(Text.of(TextColors.LIGHT_PURPLE, TextStyles.BOLD, "PokeCatch event!"))
                .subtitle(Text.of(TextColors.WHITE, "Catch as many Pokémon as you can!")).stay(80).build();
        return title;
    }

    public static void updateScoreboard() {
        Main.scoreboard = Scoreboard.builder().build();
        if (Main.pokecatch){
            Main.obj = Objective.builder().name("Score").criterion(Criteria.DUMMY).displayName(Text.of(TextColors.GREEN, TextStyles.BOLD, "PokeCatch Event")).build();
        }
        //Main.obj.getOrCreateScore(Text.of(TextColors.WHITE, "Your Score:")).setScore(0);
        Main.obj.getOrCreateScore(Text.of(TextColors.RED, "--------")).setScore(-2);
        Main.obj.getOrCreateScore(Text.of(TextColors.GOLD, "Rewards:")).setScore(-3);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.GREEN, "1st -", TextStyles.ITALIC, " somereward")).setScore(-4);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.RED, "2nd -", TextStyles.ITALIC, " somereward")).setScore(-5);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.DARK_BLUE, "3rd -", TextStyles.ITALIC, " somereward")).setScore(-6);
        Main.obj.getOrCreateScore(Text.of("")).setScore(-7);
        Main.obj.getOrCreateScore(Text.of(TextColors.LIGHT_PURPLE, "Time Left: ", Main.scoreboardtimer)).setScore(-8);
        Main.scoreboard.addObjective(Main.obj);
        Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
    }

    public static Map<String, Integer> getTop() {
        int counter = 1;
        int top = 0;
        int max = 0;
        String topname = null;
        Map<String, Integer> temp = new HashMap<>();
        Map<String, Integer> end = new HashMap<>();

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
            //Main.obj.getOrCreateScore(Text.of(topname)).setScore(top);
            temp.remove(topname);
            end.put(topname, top);
            counter++;
            topname = null;
            top = 0;
        }
        return end;
    }
}
