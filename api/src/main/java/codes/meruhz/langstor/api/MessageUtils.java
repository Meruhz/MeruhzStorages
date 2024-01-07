package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public interface MessageUtils<T> {

    default @NotNull String localeToString(@NotNull Locale locale) {
        @NotNull String language = locale.getLanguage().toUpperCase();
        @NotNull String country = locale.getCountry().toUpperCase();

        if(language.isEmpty() || country.isEmpty()) {
            throw new NullPointerException("Locale must have language and a country");
        }

        return language + "_" + country;
    }

    default @NotNull Locale stringToLocale(@NotNull String string) {
        if(!string.contains("_")) {
            throw new IllegalArgumentException("Invalid locale string");
        }

        String @NotNull [] parts = string.split("_");

        if(parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid locale string format");
        }

        @NotNull String language = parts[0].toLowerCase();
        @NotNull String country = parts[1].toUpperCase();

        return new Locale(language, country);
    }

    @NotNull T replaceText(@NotNull T text, Object @NotNull [] replaces);

    @NotNull List<@NotNull T> replaceArray(@NotNull List<@NotNull T> arrayText, Object @NotNull [] replaces);
}
