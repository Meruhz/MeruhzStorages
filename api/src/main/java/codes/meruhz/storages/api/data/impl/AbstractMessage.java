package codes.meruhz.storages.api.data.impl;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.Content;
import codes.meruhz.storages.api.data.Message;
import codes.meruhz.storages.api.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractMessage<T> implements Message<T> {

    private final @NotNull Map<@NotNull Locale, @NotNull Content<@NotNull T>> contents = new LinkedHashMap<>();

    private final @NotNull Storage<@NotNull T> storage;
    private final @NotNull String id;

    public AbstractMessage(@NotNull Storage<@NotNull T> storage, @NotNull String id) {
        this.storage = storage;
        this.id = id;
        this.getStorage().getMessages().add(this);
    }
    protected @NotNull Map<@NotNull Locale, @NotNull Content<@NotNull T>> getContentsMap() {
        return this.contents;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull Storage<@NotNull T> getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull Collection<@NotNull Content<@NotNull T>> getContents() {
        return this.getContentsMap().values();
    }

    @Override
    public @NotNull T getText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return this.getContentsMap().get(locale).replace(replaces);

        } catch (NullPointerException ex) {
            try {
                return this.getContentsMap().get(this.getStorage().getDefaultLocale()).replace(replaces);

            } catch (NullPointerException e) {
                throw new NullPointerException("Could not be found normal text with locales '" + locale + "' and '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
            }
        }
    }

    @Override
    public void addContent(@NotNull Content<@NotNull T> content) {
        this.getContentsMap().put(content.getLocale(), content);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractMessage<?> that = (AbstractMessage<?>) o;

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
