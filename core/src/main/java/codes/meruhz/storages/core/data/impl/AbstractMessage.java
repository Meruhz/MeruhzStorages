package codes.meruhz.storages.core.data.impl;

import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMessage<M, L> implements Message<M, L> {

    private final @NotNull Map<@NotNull L, @NotNull List<M>> arrayContents = new LinkedHashMap<>();
    private final @NotNull Map<@NotNull L, @NotNull M> contents = new LinkedHashMap<>();

    private final @NotNull Storage<M, L> storage;
    private final @NotNull String id;

    public AbstractMessage(@NotNull Storage<M, L> storage, @NotNull String id) {
        if(storage.getMessage(id).isPresent()) {
            throw new IllegalArgumentException("Message '" + id + "' already exists at storage '" + storage.getName() + "'");
        }

        this.storage = storage;
        this.id = id;
    }

    public @NotNull Map<@NotNull L, @NotNull List<M>> getArrayContents() {
        return this.arrayContents;
    }

    public @NotNull Map<@NotNull L, @NotNull M> getContents() {
        return this.contents;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull Storage<M, L> getStorage() {
        return this.storage;
    }

    @Override
    public boolean isArrayText(@NotNull L locale) {
        return this.getArrayContents().containsKey(locale);
    }

    @Override
    public void addArrayContent(@NotNull L locale, @NotNull List<M> content) {
        this.getArrayContents().computeIfAbsent(locale, key -> new LinkedList<>(content));
        this.getStorage().getMessages().add(this);
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
