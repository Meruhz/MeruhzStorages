package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * This interface provides utility methods for handling messages and locales.
 *
 * @param <T> The type of text content used in messages, expected to be non-null.
 */
public interface MessageUtils<T> {

    /**
     * Converts a {@link Locale} object to its string representation in the format "language_country".
     *
     * @param locale The locale to convert.
     * @return A non-null string representing the locale.
     * @throws NullPointerException If the provided locale does not have both language and country.
     */
    default @NotNull String localeToString(@NotNull Locale locale) {
        @NotNull String language = locale.getLanguage().toUpperCase();
        @NotNull String country = locale.getCountry().toUpperCase();

        if (language.isEmpty() || country.isEmpty()) {
            throw new NullPointerException("Locale must have language and a country");
        }

        return language + "_" + country;
    }

    /**
     * Converts a locale string in the format "language_country" to a {@link Locale} object.
     *
     * @param string The locale string to convert.
     * @return A non-null instance of {@link Locale} representing the converted locale.
     * @throws IllegalArgumentException If the provided string is an invalid locale format.
     */
    default @NotNull Locale stringToLocale(@NotNull String string) {
        if (!string.contains("_")) {
            throw new IllegalArgumentException("Invalid locale string");
        }

        String @NotNull [] parts = string.split("_");

        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid locale string format");
        }

        @NotNull String language = parts[0].toLowerCase();
        @NotNull String country = parts[1].toUpperCase();

        return new Locale(language, country);
    }

    /**
     * Replaces placeholders in a single text content with the provided values.
     *
     * @param text     The text content to replace placeholders in.
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A new instance of text content after replacement.
     */
    @NotNull T replaceText(@NotNull T text, Object @NotNull [] replaces);

    /**
     * Replaces placeholders in a list of text content with the provided values.
     *
     * @param arrayText The list of text content to replace placeholders in.
     * @param replaces  An array of objects to replace placeholders in the text content.
     * @return A non-null list of text content instances after replacement.
     */
    @NotNull List<@NotNull T> replaceArray(@NotNull List<@NotNull T> arrayText, Object @NotNull [] replaces);
}
