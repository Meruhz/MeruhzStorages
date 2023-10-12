package com.storages.core.data.providers;

import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractMessage<M, L> implements Message<M, L> {

    private final @NotNull Map<@NotNull L, @NotNull M> contents = new LinkedHashMap<>();

    private final @NotNull Storage<M, L> storage;
    private final @NotNull String id;

    public AbstractMessage(@NotNull Storage<M, L> storage, @NotNull String id) {
        this.storage = storage;
        this.id = id;
    }

    public @NotNull Map<@NotNull L, @NotNull M> getContents() {
        return this.contents;
    }

    @Override
    public @NotNull Storage<M, L> getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull M getText(@NotNull L locale) {
        try {
            return this.getContents().get(locale);

        } catch (NullPointerException ex) {
            try {
                return this.getContents().get(this.getStorage().getDefaultLocale());

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale and the default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "'");
            }
        }
    }

    @Override
    public void addContent(@NotNull L locale, @NotNull M content) {
        this.getContents().put(locale, content);
        this.getStorage().getMessages().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractMessage<?, ?> that = (AbstractMessage<?, ?>) o;

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
