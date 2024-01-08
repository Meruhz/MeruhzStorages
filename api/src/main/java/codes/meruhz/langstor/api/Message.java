package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This interface defines the contract for a message in a language storage system.
 *
 * @param <T> The type of text content in the message.
 */
public interface Message<T> {

    /**
     * Gets the unique identifier of the message.
     *
     * @return The ID of the message.
     */
    @NotNull String getId();

    /**
     * Gets the storage associated with the message.
     *
     * @return The message storage.
     */

    @NotNull MessageStorage<T> getStorage();

    /**
     * Gets a collection of contents associated with the message.
     *
     * @return The collection of message contents.
     */
    @NotNull Collection<@NotNull MessageContent<T>> getContents();

    /**
     * Gets the message content for the specified locale.
     *
     * @param locale The locale for which to retrieve the content.
     * @return The message content for the specified locale.
     * @throws NullPointerException If the content for the specified locale is not found.
     */
    default @NotNull MessageContent<T> getContent(@NotNull Locale locale) {
        return this.getContents().stream().filter(messageContent -> messageContent.getLocale().equals(locale)).findFirst().orElseThrow(() -> new NullPointerException("Content with locale '" + locale + "' could not be found for message with id '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'"));
    }

    /**
     * Gets the text of the message for the specified locale, with optional replacements.
     *
     * @param locale   The locale for which to retrieve the text.
     * @param replaces An array of objects to replace placeholders in the text.
     * @return The formatted text for the specified locale.
     * @throws NullPointerException If the text for the specified locale is not found.
     */
    default @NotNull T getText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return this.getContent(locale).replace(replaces);

        } catch (NullPointerException e) {

            try {
                return this.getContent(this.getStorage().getDefaultLocale()).replace(replaces);

            } catch (NullPointerException ex) {
                throw new NullPointerException("Could not be found text with locales '" + locale + "' and the default '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
            }
        }
    }

    /**
     * Gets the text of the message with optional replacements, using the default locale.
     *
     * @param replaces An array of objects to replace placeholders in the text.
     * @return The formatted text for the default locale.
     * @throws NullPointerException If the text for the default locale is not found.
     */
    default @NotNull T getText(Object @NotNull [] replaces) {
        try {
            return this.getContent(this.getStorage().getDefaultLocale()).replace(replaces);

        } catch (NullPointerException e) {
            throw new NullPointerException("Could not be found text with default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
        }
    }

    /**
     * Gets an array of text for the specified locale, with optional replacements.
     *
     * @param locale   The locale for which to retrieve the array of text.
     * @param replaces An array of objects to replace placeholders in the text.
     * @return The formatted array of text for the specified locale.
     * @throws NullPointerException If the array text for the specified locale is not found.
     */
    default @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return this.getContent(locale).replaceArray(replaces);

        } catch (NullPointerException e) {

            try {
                return this.getContent(this.getStorage().getDefaultLocale()).replaceArray(replaces);

            } catch (NullPointerException ex) {
                throw new NullPointerException("Could not be found array text with locales '" + locale + "' and the default '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
            }
        }
    }

    /**
     * Gets an array of text with optional replacements, using the default locale.
     *
     * @param replaces An array of objects to replace placeholders in the text.
     * @return The formatted array of text for the default locale.
     * @throws NullPointerException If the array text for the default locale is not found.
     */
    default @NotNull List<@NotNull T> getArrayText(Object @NotNull [] replaces) {
        try {
            return this.getContent(this.getStorage().getDefaultLocale()).replaceArray(replaces);

        } catch (NullPointerException e) {
            throw new NullPointerException("Could not be found array text with default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
        }
    }

    /**
     * Asynchronously adds a new content to the message.
     *
     * @param content The message content to be added.
     * @return A CompletableFuture indicating the success or failure of the operation.
     */
    default @NotNull CompletableFuture<Void> addContent(@NotNull MessageContent<T> content) {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
           try {

               if(!this.getContents().contains(content)) {
                   this.getContents().add(content);
               }

               completableFuture.complete(null);

           } catch (Exception e) {
               completableFuture.completeExceptionally(e);
           }
        });

        return completableFuture.orTimeout(2, TimeUnit.SECONDS);
    }
}
