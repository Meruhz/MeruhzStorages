package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageStorage;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.utils.model.JsonElementConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * This abstract class provides a base implementation for the {@link MessageStorage} interface.
 * It includes essential properties such as the storage name, default locale, set of messages,
 * and JSON model configuration.
 *
 * @param <T> The type of text content used in messages, expected to be non-null.
 */
public abstract class AbstractMessageStorage<T> implements MessageStorage<T> {

    private final @NotNull String name;
    private final @NotNull Locale defaultLocale;
    private final @NotNull Set<@NotNull Message<T>> messages;
    private final @NotNull JsonElementConfiguration jsonModel;

    /**
     * Constructs an AbstractMessageStorage with the specified name and default locale.
     *
     * @param name          The unique name for the storage.
     * @param defaultLocale The default locale for the storage.
     */
    public AbstractMessageStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        this(name, defaultLocale, new LinkedHashSet<>());
    }

    /**
     * Constructs an AbstractMessageStorage with the specified name, default locale, and initial set of messages.
     *
     * @param name          The unique name for the storage.
     * @param defaultLocale The default locale for the storage.
     * @param messages      The initial set of messages associated with the storage.
     */
    public AbstractMessageStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<T>> messages) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = messages;
        this.jsonModel = new JsonElementConfiguration(LanguageStorage.getStorageFolder(), this.getName());
        LanguageStorage.getLanguageApi().getStorages().add(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull JsonElementConfiguration getJsonModel() {
        return this.jsonModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<@NotNull Message<T>> getMessages() {
        return this.messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMessageStorage<?> that = (AbstractMessageStorage<?>) o;

        return name.equals(that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
