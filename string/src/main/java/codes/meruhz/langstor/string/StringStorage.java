package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.providers.AbstractMessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Set;

/**
 * This class represents a storage for messages containing strings. It extends
 * AbstractMessageStorage and provides constructors for creating a StringStorage with a specified
 * name and default locale, as well as with additional messages.
 *
 * @see AbstractMessageStorage
 */
public class StringStorage extends AbstractMessageStorage<String> {

    /**
     * Constructs a StringStorage with the specified name and default locale.
     *
     * @param name          The name of the storage.
     * @param defaultLocale The default locale of the storage.
     */
    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    /**
     * Constructs a StringStorage with the specified name, default locale, and additional messages.
     *
     * @param name          The name of the storage.
     * @param defaultLocale The default locale of the storage.
     * @param messages      The set of additional messages to be included in the storage.
     */
    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<String>> messages) {
        super(name, defaultLocale, messages);
    }
}
