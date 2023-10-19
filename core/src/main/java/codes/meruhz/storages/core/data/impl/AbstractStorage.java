package codes.meruhz.storages.core.data.impl;

import codes.meruhz.storages.core.StoragesCore;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractStorage<M, L> implements Storage<M, L> {

    private final @NotNull String name;
    private final @NotNull L defaultLocale;
    private final @NotNull Set<@NotNull Message<M, L>> messages;

    private @NotNull JsonConfiguration jsonContent;

    protected boolean loaded;

    public AbstractStorage(@NotNull String name, @NotNull L defaultLocale) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>();
        this.jsonContent = new JsonConfiguration(StoragesCore.getCore().getStoragesDirectory(), name);
    }

    public @NotNull JsonConfiguration getJsonContent() {
        return this.jsonContent;
    }

    public void setJsonContent(@NotNull JsonConfiguration jsonContent) {
        this.jsonContent = jsonContent;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull L getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message<M, L>> getMessages() {
        return this.messages;
    }

    @Override
    public @NotNull M getText(@NotNull L locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull Optional<Message<M, L>> optionalMessage = this.getMessage(id);

        if(!optionalMessage.isPresent()) {
            throw new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'");
        }

        @NotNull AbstractMessage<M, L> message = (AbstractMessage<M, L>) optionalMessage.get();

        if(!message.getContents().containsKey(locale)) {
            throw new NullPointerException("Could not be found the specified locale from message '" + id + "' at storage '" + this.getName() + "'");
        }

        return message.replace(locale, replaces);
    }

    @Override
    public boolean isLoaded() {
        return this.loaded;
    }
}
