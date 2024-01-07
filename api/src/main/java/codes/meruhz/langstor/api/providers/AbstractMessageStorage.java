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

public abstract class AbstractMessageStorage<T> implements MessageStorage<T> {

    private final @NotNull String name;
    private final @NotNull Locale defaultLocale;
    private final @NotNull Set<@NotNull Message<T>> messages;
    private final @NotNull JsonElementConfiguration jsonModel;

    public AbstractMessageStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        this(name, defaultLocale, new LinkedHashSet<>());
    }

    public AbstractMessageStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<T>> messages) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = messages;
        this.jsonModel = new JsonElementConfiguration(LanguageStorage.getStorageFolder(), this.getName());
        LanguageStorage.getLanguageApi().getStorages().add(this);
    }

    @Override
    public final @NotNull String getName() {
        return this.name;
    }

    @Override
    public final @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public final @NotNull JsonElementConfiguration getJsonModel() {
        return this.jsonModel;
    }

    @Override
    public @NotNull Collection<@NotNull Message<T>> getMessages() {
        return this.messages;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractMessageStorage<?> that = (AbstractMessageStorage<?>) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
