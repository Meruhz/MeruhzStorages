package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.LanguageApi;
import codes.meruhz.langstor.api.MessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This abstract class serves as a base implementation for the {@link LanguageApi} interface.
 * It includes a set to manage multiple {@link MessageStorage} instances associated with the language API.
 */
public abstract class AbstractLanguageApi implements LanguageApi {

    private final @NotNull Set<@NotNull MessageStorage<?>> storages;

    /**
     * Constructs an AbstractLanguageApi instance with an empty set of message storages.
     */
    public AbstractLanguageApi() {
        this(new HashSet<>());
    }

    /**
     * Constructs an AbstractLanguageApi instance with the specified set of message storages.
     *
     * @param storages The set of message storages to be associated with the language API.
     */
    public AbstractLanguageApi(@NotNull Set<@NotNull MessageStorage<?>> storages) {
        this.storages = storages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<@NotNull MessageStorage<?>> getStorages() {
        return this.storages;
    }
}
