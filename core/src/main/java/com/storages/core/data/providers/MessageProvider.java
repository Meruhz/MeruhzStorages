package com.storages.core.data.providers;

import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.utils.LocaleUtils;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public abstract class MessageProvider<M> implements Message<M> {

    private final @NotNull Map<@NotNull Locale, @NotNull M> contents = new LinkedHashMap<>();

    private final @NotNull Storage<M> storage;
    private final @NotNull String id;

    public MessageProvider(@NotNull Storage<M> storage, @NotNull String id) {
        this.storage = storage;
        this.id = id;
    }

    public @NotNull Map<@NotNull Locale, @NotNull M> getContents() {
        return this.contents;
    }

    @Override
    public @NotNull Storage<M> getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull Locale @NotNull [] getLocales() {
        return this.getContents().keySet().toArray(new Locale[0]);
    }

    @Override
    public @NotNull M getText(@NotNull Locale locale) {
        try {
            return this.getContents().get(locale);

        } catch (NullPointerException ex) {
            try {
                return this.getContents().get(this.getStorage().getDefaultLocale());

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find specified locale '" + LocaleUtils.toString(locale) + "' and the default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "'");
            }
        }
    }

    @Override
    public void addContent(@NotNull Locale locale, @NotNull M content) {
        this.getContents().put(locale, content);
        this.getStorage().getMessages().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        MessageProvider<?> that = (MessageProvider<?>) o;

        if(!storage.equals(that.storage)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = storage.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
