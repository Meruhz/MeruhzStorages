package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides utility methods for working with strings in the context of language storage.
 * It implements the MessageUtils interface for strings.
 *
 * @see MessageUtils
 */
public class StringUtils implements MessageUtils<String> {

    /**
     * Replaces placeholders in the given text with the specified values.
     *
     * @param text     The text containing placeholders.
     * @param replaces The values to replace the placeholders with.
     * @return The text with replaced placeholders.
     */
    @Override
    public @NotNull String replaceText(@NotNull String text, Object @NotNull [] replaces) {
        for (Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
        }
        return text;
    }

    /**
     * Replaces placeholders in each element of the given list of strings with the specified values.
     *
     * @param arrayText The list of strings containing placeholders.
     * @param replaces  The values to replace the placeholders with.
     * @return The list of strings with replaced placeholders.
     */
    @Override
    public @NotNull List<@NotNull String> replaceArray(@NotNull List<@NotNull String> arrayText, Object @NotNull [] replaces) {
        return arrayText.stream().map(text -> this.replaceText(text, replaces)).collect(Collectors.toList());
    }
}
