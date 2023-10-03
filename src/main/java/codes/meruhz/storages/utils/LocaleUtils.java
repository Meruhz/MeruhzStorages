package codes.meruhz.storages.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class LocaleUtils {

    private LocaleUtils() {

    }

    public static @NotNull String toString(@NotNull Locale locale) {
        @NotNull String language = locale.getLanguage().toUpperCase();
        @NotNull String country = locale.getCountry().toUpperCase();

        if(language.isEmpty() || country.isEmpty()) {
            throw new NullPointerException("Locale must have language and a country");
        }

        return language + "_" + country;
    }

    public static @NotNull Locale toLocale(@NotNull String str) {
        @NotNull String[] split = str.split("_");

        return new Locale(split[0], split.length > 1 ? split[1] : "");
    }
}
