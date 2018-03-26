package me.pokerman99.PokeEvents.utils;

import me.pokerman99.PokeEvents.Main;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class Scoreboards {

    public static void updateScoreboardPokeCatch() {
        Main.scoreboard = Scoreboard.builder().build();
        Main.obj = Objective.builder().name("Score").criterion(Criteria.DUMMY).displayName(Text.of(TextColors.GREEN, TextStyles.BOLD, "PokeCatch Event")).build();
        Main.obj.getOrCreateScore(Text.of(TextColors.RED, "")).setScore(-1);
        Main.obj.getOrCreateScore(Text.of(TextColors.RED, "Time Left: ", Main.scoreboardtimerpokecatch)).setScore(-2);
        Main.obj.getOrCreateScore(Text.of("")).setScore(-3);
        Main.obj.getOrCreateScore(Text.of(TextColors.GOLD, "Rewards:")).setScore(-4);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.GREEN, "1st -", TextStyles.ITALIC, " ", Main.rewardinfo1pokecatch)).setScore(-5);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.RED, "2nd -", TextStyles.ITALIC, " ", Main.rewardinfo2pokecatch)).setScore(-6);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.DARK_BLUE, "3rd -", TextStyles.ITALIC, " ", Main.rewardinfo3pokecatch)).setScore(-7);
        Main.scoreboard.addObjective(Main.obj);
        Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
    }

    public static void updateScoreboardPokeBattle() {
        Main.scoreboard = Scoreboard.builder().build();
        Main.obj = Objective.builder().name("Score").criterion(Criteria.DUMMY).displayName(Text.of(TextColors.GREEN, TextStyles.BOLD, "PokeBattle Event")).build();
        Main.obj.getOrCreateScore(Text.of(TextColors.RED, "")).setScore(-1);
        Main.obj.getOrCreateScore(Text.of(TextColors.RED, "Time Left: ", Main.scoreboardtimerpokebattle)).setScore(-2);
        Main.obj.getOrCreateScore(Text.of("")).setScore(-3);
        Main.obj.getOrCreateScore(Text.of(TextColors.GOLD, "Rewards:")).setScore(-4);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.GREEN, "1st -", TextStyles.ITALIC, " ", Main.rewardinfo1pokebattle)).setScore(-5);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.RED, "2nd -", TextStyles.ITALIC, " ", Main.rewardinfo2pokebattle)).setScore(-6);
        Main.obj.getOrCreateScore(Text.of("   ", TextColors.DARK_BLUE, "3rd -", TextStyles.ITALIC, " ", Main.rewardinfo3pokebattle)).setScore(-7);
        Main.scoreboard.addObjective(Main.obj);
        Main.scoreboard.updateDisplaySlot(Main.obj, DisplaySlots.SIDEBAR);
    }
}
