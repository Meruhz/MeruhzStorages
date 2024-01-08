package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This class represents a message containing BungeeCord's BaseComponent[] and extends
 * the AbstractMessage class. It provides methods for retrieving legacy text and arrays
 * for a specified locale and message identifier.
 *
 * @see AbstractMessage
 */
public class ComponentMessage extends AbstractMessage<BaseComponent[]> {

    /**
     * Constructs a ComponentMessage with the specified identifier and storage.
     *
     * @param id      The unique identifier for the message.
     * @param storage The storage to which the message belongs.
     */
    public ComponentMessage(@NotNull String id, @NotNull ComponentStorage storage) {
        super(id, storage);
    }

    /**
     * Constructs a ComponentMessage with the specified identifier, storage, and initial set of message contents.
     *
     * @param id               The unique identifier for the message.
     * @param storage          The storage to which the message belongs.
     * @param messageContents The initial set of message contents associated with the message.
     */
    public ComponentMessage(@NotNull String id, @NotNull ComponentStorage storage, @NotNull Set<@NotNull MessageContent<BaseComponent[]>> messageContents) {
        super(id, storage, messageContents);
    }

    /**
     * Retrieves the legacy text for the specified locale and replaces, using the default message content.
     *
     * @param locale   The locale for which to retrieve the text.
     * @param replaces The objects to replace placeholders in the text.
     * @return The legacy text as a string.
     */
    public @NotNull String getLegacyText(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyText(super.getText(locale, replaces));
    }

    /**
     * Retrieves the legacy text for the default locale and replaces, using the default message content.
     *
     * @param replaces The objects to replace placeholders in the text.
     * @return The legacy text as a string.
     */
    public @NotNull String getLegacyText(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyText(super.getText(replaces));
    }

    /**
     * Retrieves the legacy text array for the specified locale and replaces, using the default message content.
     *
     * @param locale   The locale for which to retrieve the text array.
     * @param replaces The objects to replace placeholders in the text array.
     * @return The legacy text array as a list of strings.
     */
    public @NotNull List<@NotNull String> getLegacyArray(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyArray(super.getArrayText(locale, replaces));
    }

    /**
     * Retrieves the legacy text array for the default locale and replaces, using the default message content.
     *
     * @param replaces The objects to replace placeholders in the text array.
     * @return The legacy text array as a list of strings.
     */
    public @NotNull List<@NotNull String> getLegacyArray(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyArray(super.getArrayText(replaces));
    }
}
