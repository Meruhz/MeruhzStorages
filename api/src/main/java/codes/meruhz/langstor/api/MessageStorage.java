package codes.meruhz.langstor.api;

import codes.meruhz.langstor.api.utils.model.JsonElementConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * This interface defines a message storage abstraction responsible for managing
 * messages with associated content. It provides methods to retrieve information
 * about the storage, such as its name, default locale, JSON model configuration,
 * and the collection of messages it contains.
 *
 * @param <T> The type of text content used in messages, expected to be non-null.
 */
public interface MessageStorage<T> {

    /**
     * Gets the name of the message storage.
     *
     * @return A non-null string representing the name of the storage.
     */
    @NotNull String getName();

    /**
     * Gets the default locale for the message storage.
     *
     * @return A non-null instance of {@link Locale} representing the default locale.
     */
    @NotNull Locale getDefaultLocale();

    /**
     * Gets the JSON model configuration associated with the message storage.
     *
     * @return A non-null instance of {@link JsonElementConfiguration}.
     */
    @NotNull JsonElementConfiguration getJsonModel();

    /**
     * Gets the collection of messages stored in this message storage.
     *
     * @return A non-null collection of {@link Message} instances.
     */
    @NotNull Collection<@NotNull Message<T>> getMessages();

    /**
     * Gets a specific message from the storage based on its ID.
     *
     * @param id The ID of the message to retrieve.
     * @return A non-null instance of {@link Message}.
     * @throws NullPointerException If the message with the specified ID is not found.
     */
    default @NotNull Message<T> getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().orElseThrow(() -> new NullPointerException("Message with id '" + id + "' could not be found at storage '" + this.getName() + "'"));
    }

    /**
     * Retrieves the text content of a message in the default locale with placeholder replacement.
     *
     * @param id       The ID of the message.
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A non-null instance of the text content after replacement.
     */
    default @NotNull T getText(@NotNull String id, @NotNull Object... replaces) {
        return this.getText(this.getDefaultLocale(), id, replaces);
    }

    /**
     * Retrieves the text content of a message in a specified locale with placeholder replacement.
     *
     * @param locale   The locale for which to retrieve the text content.
     * @param id       The ID of the message.
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A non-null instance of the text content after replacement.
     */
    default @NotNull T getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getContent(locale).replace(replaces);
    }

    /**
     * Retrieves the text content of a message in the default locale as an array with placeholder replacement.
     *
     * @param id       The ID of the message.
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A non-null list of text content instances after replacement.
     */
    default @NotNull List<@NotNull T> getArrayText(@NotNull String id, @NotNull Object... replaces) {
        return this.getArrayText(this.getDefaultLocale(), id, replaces);
    }

    /**
     * Retrieves the text content of a message in a specified locale as an array with placeholder replacement.
     *
     * @param locale   The locale for which to retrieve the text content.
     * @param id       The ID of the message.
     * @param replaces An array of objects to replace placeholders in the text content.
     * @return A non-null list of text content instances after replacement.
     */
    default @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getContent(locale).replaceArray(replaces);
    }
}
