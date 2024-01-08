package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * This interface represents a message content abstraction that encapsulates information
 * about a message, its locale, and the corresponding text. Implementations of this interface
 * are expected to provide methods for retrieving the message, locale, text, replacing text placeholders,
 * and other related operations.
 *
 * @param <T> The type of text content, which is expected to be non-null.
 */
public interface MessageContent<T> {

    /**
     * Gets the message associated with this content.
     *
     * @return A non-null instance of {@link Message}.
     */
    @NotNull Message<@NotNull T> getMessage();

    /**
     * Gets the locale of the message content.
     *
     * @return A non-null instance of {@link Locale}.
     */
    @NotNull Locale getLocale();

    /**
     * Gets the text content of the message.
     *
     * @return A non-null instance of the text content.
     */
    @NotNull T getText();

    /**
     * Replaces placeholders in the text content with the provided values.
     *
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A new instance of text content after replacement.
     */
    @NotNull T replace(Object @NotNull [] replaces);

    /**
     * Replaces placeholders in the text content with the provided values and returns
     * the result as a list.
     *
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A non-null list of text content instances after replacement.
     */
    @NotNull List<@NotNull T> replaceArray(Object @NotNull [] replaces);

    /**
     * Gets the text content as a list. If the original text is not an array, it will be
     * wrapped in a singleton list.
     *
     * @return A non-null list of text content instances.
     */
    @NotNull List<@NotNull T> getAsArrayText();

    /**
     * Checks if the text content is an array.
     *
     * @return {@code true} if the text content is an array; otherwise, {@code false}.
     */
    boolean isArrayText();
}
