package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageContent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * This class represents the content of a message containing strings. It extends AbstractMessageContent
 * and provides methods for replacing text and array text with placeholders.
 *
 * @see AbstractMessageContent
 */
public class StringContent extends AbstractMessageContent<String> {

    /**
     * Constructs a StringContent with the specified message, locale, and text.
     *
     * @param message The message to which this content belongs.
     * @param locale  The locale for this content.
     * @param text    The text.
     */
    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull String text) {
        super(message, locale, text);
    }

    /**
     * Constructs a StringContent with the specified message, locale, and array text.
     *
     * @param message   The message to which this content belongs.
     * @param locale    The locale for this content.
     * @param arrayText The array text represented as a list of strings.
     */
    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull List<@NotNull String> arrayText) {
        super(message, locale, arrayText);
    }

    /**
     * Replaces placeholders in the text with the provided values.
     *
     * @param replaces The values to replace the placeholders with.
     * @return The updated text after replacement.
     */
    @Override
    public @NotNull String replace(Object @NotNull [] replaces) {
        return ((StringUtils) LanguageStorage.getMessageUtils()).replaceText(super.getText(), replaces);
    }

    /**
     * Replaces placeholders in the array text with the provided values.
     *
     * @param replaces The values to replace the placeholders with.
     * @return The updated array text after replacement.
     */
    @Override
    public @NotNull List<@NotNull String> replaceArray(Object @NotNull [] replaces) {
        return ((StringUtils) LanguageStorage.getMessageUtils()).replaceArray(super.getAsArrayText(), replaces);
    }
}
