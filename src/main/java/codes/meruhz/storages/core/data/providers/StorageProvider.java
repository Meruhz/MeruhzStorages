package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.MeruhzStorages;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.utils.LocaleUtils;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StorageProvider implements Storage {

    private final @NotNull String name;
    private final @NotNull Locale defaultLocale;
    private final @NotNull Set<@NotNull Message> messages;

    private @Nullable JsonConfiguration json;

    public StorageProvider(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Message... messages) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>(Arrays.asList(messages));
    }

    public @NotNull JsonConfiguration getJson() {
        return Optional.ofNullable(this.json).orElseThrow(() -> new NullPointerException("The storage must be initialized through API"));
    }

    public void setJson(@NotNull JsonConfiguration json) {
        this.json = json;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message> getMessages() {
        return this.messages;
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull Optional<Message> optionalMessage = this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst();

        if(!optionalMessage.isPresent()) {
            throw new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'");
        }

        @NotNull MessageProvider message = (MessageProvider) optionalMessage.get();

        if(!message.getContents().containsKey(locale)) {
            throw new NullPointerException("Could not be found locale '" + LocaleUtils.toString(locale) + "' from message '" + id + "' at storage '" + this.getName() + "'");
        }

        return message.replace(locale, replaces);
    }

    @Override
    public void save() {
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
