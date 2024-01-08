package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageStorage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This class represents a storage for messages containing BungeeCord's BaseComponent[].
 * It extends the AbstractMessageStorage class and provides methods for retrieving
 * legacy text and arrays for a specified locale and message identifier.
 *
 * @see AbstractMessageStorage
 */
public class ComponentStorage extends AbstractMessageStorage<BaseComponent[]> {

    /**
     * Constructs a ComponentStorage with the specified name and default locale.
     *
     * @param name          The unique name for the storage.
     * @param defaultLocale The default locale for the storage.
     */
    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    /**
     * Constructs a ComponentStorage with the specified name, default locale, and initial set of messages.
     *
     * @param name          The unique name for the storage.
     * @param defaultLocale The default locale for the storage.
     * @param messages      The initial set of messages associated with the storage.
     */
    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<BaseComponent[]>> messages) {
        super(name, defaultLocale, messages);
    }

    /**
     * Retrieves the legacy text for the specified locale, message identifier, and replaces.
     *
     * @param locale    The locale for which to retrieve the text.
     * @param id        The identifier of the message.
     * @param replaces  The objects to replace placeholders in the text.
     * @return The legacy text as a string.
     */
    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyText(super.getMessage(id).getText(locale, replaces));
    }

    /**
     * Retrieves the legacy text for the specified message identifier and replaces, using the default locale.
     *
     * @param id       The identifier of the message.
     * @param replaces The objects to replace placeholders in the text.
     * @return The legacy text as a string.
     */
    public @NotNull String getLegacyText(@NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyText(super.getMessage(id).getText(replaces));
    }

    /**
     * Retrieves the legacy text array for the specified message identifier, locale, and replaces.
     *
     * @param id        The identifier of the message.
     * @param locale    The locale for which to retrieve the text array.
     * @param replaces  The objects to replace placeholders in the text array.
     * @return The legacy text array as a list of strings.
     */
    public @NotNull List<@NotNull String> getLegacyArray(@NotNull String id, @NotNull Locale locale, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyArray(super.getMessage(id).getArrayText(locale, replaces));
    }

    /**
     * Retrieves the legacy text array for the specified message identifier and replaces, using the default locale.
     *
     * @param id        The identifier of the message.
     * @param replaces  The objects to replace placeholders in the text array.
     * @return The legacy text array as a list of strings.
     */
    public @NotNull List<@NotNull String> getLegacyArray(@NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).getLegacyArray(super.getMessage(id).getArrayText(replaces));
    }
}
