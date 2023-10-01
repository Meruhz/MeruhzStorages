package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class StorageProvider implements Storage {

    private final @NotNull String name;
    private final @NotNull LocaleEnum defaultLocale;
    private final @NotNull Set<@NotNull Message> messages;

    private @Nullable JsonConfiguration json;

    public StorageProvider(@NotNull String name, @NotNull LocaleEnum defaultLocale, @NotNull Message... messages) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>(Arrays.asList(messages));
    }

    public @NotNull JsonConfiguration getJson() {
        return Optional.ofNullable(this.json).orElseThrow(() -> new NullPointerException("Storage '" + this.getName() + "' must be initialized using the API"));
    }

    public void setJson(@NotNull JsonConfiguration json) {
        this.json = json;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull LocaleEnum getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message> getMessages() {
        return this.messages;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        StorageProvider that = (StorageProvider) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
