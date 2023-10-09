package com.storages.core.data.providers;

import com.storages.core.StoragesCore;
import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.utils.LocaleUtils;
import com.storages.core.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public abstract class StorageProvider<M> implements Storage<M> {

    private final @NotNull String name;
    private final @NotNull Locale defaultLocale;
    private final @NotNull Set<@NotNull Message<M>> messages;

    private @NotNull JsonConfiguration jsonContent;

    public StorageProvider(@NotNull String name, @NotNull Locale defaultLocale) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>();
        this.jsonContent = new JsonConfiguration(StoragesCore.getDataFolder(), name);
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
    public @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message<M>> getMessages() {
        return this.messages;
    }

    @Override
    public @NotNull Optional<Message<M>> getMessage(@NotNull String id) {
        return Storage.super.getMessage(id);
    }

    @Override
    public @NotNull M getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull Optional<Message<M>> optionalMessage = this.getMessage(id);

        if(!optionalMessage.isPresent()) {
            throw new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'");
        }

        @NotNull MessageProvider<M> message = (MessageProvider<M>) optionalMessage.get();

        if(!message.getContents().containsKey(locale)) {
            throw new NullPointerException("Could not be found locale '" + LocaleUtils.toString(locale) + "' from message '" + id + "' at storage '" + this.getName() + "'");
        }

        return message.replace(locale, replaces);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        StorageProvider<?> that = (StorageProvider<?>) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}