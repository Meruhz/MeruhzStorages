package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.MeruhzStorages;
import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class StorageProvider implements Storage {

    private final @NotNull String name;
    private final @NotNull LocaleEnum defaultLocale;
    private final @NotNull Set<@NotNull Message> messages;

    private @Nullable JsonConfiguration json;

    public StorageProvider(@NotNull String name, @NotNull LocaleEnum defaultLocale) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>();
    }

    public @NotNull JsonConfiguration getJson() {
        return Optional.ofNullable(this.json).orElseThrow(() -> new NullPointerException("Storage '" + this.getName() + "' must be initialized through API"));
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
    public @NotNull BaseComponent @NotNull [] getText(@NotNull LocaleEnum locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull Optional<Message> optionalMessage = this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst();

        if(!optionalMessage.isPresent()) {
            throw new NullPointerException("An message with id '" + id + "' don't exists at storage '" + this.getName() + "'");
        }

        return optionalMessage.get().replace(locale, replaces);
    }

    @Override
    public void load() {
        this.getJson().setConfiguration(MeruhzStorages.getInstance().getCore().getSerializer().serialize(this), true);
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
