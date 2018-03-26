package me.pokerman99.PokeEvents.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.title.Title;

public class Titles {
    public static Title getTitlePokeCatch() {

        Title title = Title.builder()
                .title(Text.of(TextColors.LIGHT_PURPLE, TextStyles.BOLD, "PokeCatch event!"))
                .subtitle(Text.of(TextColors.WHITE, "Catch as many Pokémon as you can!")).stay(80).build();
        return title;
    }

    public static Title getTitlePokeBattle() {

        Title title = Title.builder()
                .title(Text.of(TextColors.LIGHT_PURPLE, TextStyles.BOLD, "PokeBattle event!"))
                .subtitle(Text.of(TextColors.WHITE, "Battle as many wild Pokémon as you can!")).stay(80).build();
        return title;
    }
}
