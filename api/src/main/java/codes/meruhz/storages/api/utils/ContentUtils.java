package codes.meruhz.storages.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ContentUtils {

    private ContentUtils() {

    }

    public static @NotNull String serializeLocale(@NotNull Locale locale) {
        @NotNull String language = locale.getLanguage().toUpperCase();
        @NotNull String country = locale.getCountry().toUpperCase();

        if(language.isEmpty() || country.isEmpty()) {
            throw new NullPointerException("Locale must have language and a country");
        }

        return language + "_" + country;
    }

    public static @NotNull String replace(@NotNull String text, @NotNull Object @NotNull [] replaces) {
        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
        }

        return text;
    }
}