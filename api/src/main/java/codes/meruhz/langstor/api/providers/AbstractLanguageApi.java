package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.LanguageApi;
import codes.meruhz.langstor.api.MessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractLanguageApi implements LanguageApi {

    private final @NotNull Set<@NotNull MessageStorage<?>> storages;

    public AbstractLanguageApi() {
        this(new HashSet<>());
    }

    public AbstractLanguageApi(@NotNull Set<@NotNull MessageStorage<?>> storages) {
        this.storages = storages;
    }

    @Override
    public @NotNull Collection<@NotNull MessageStorage<?>> getStorages() {
        return this.storages;
    }
}
