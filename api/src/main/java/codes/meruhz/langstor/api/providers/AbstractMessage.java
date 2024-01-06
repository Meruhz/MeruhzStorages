package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.MessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class AbstractMessage<T> implements Message<T> {

    private final @NotNull String id;
    private final @NotNull MessageStorage<T> storage;
    private final @NotNull Collection<@NotNull MessageContent<T>> contents;

    public AbstractMessage(@NotNull String id, @NotNull MessageStorage<T> storage) {
        this(id, storage, new LinkedHashSet<>());
    }

    public AbstractMessage(@NotNull String id, @NotNull MessageStorage<T> storage, @NotNull Collection<@NotNull MessageContent<T>> contents) {
        this.id = id;
        this.storage = storage;
        this.contents = contents;
        this.getStorage().getMessages().add(this);
    }

    @Override
    public final @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull MessageStorage<T> getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull Collection<@NotNull MessageContent<T>> getContents() {
        return this.contents;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractMessage<?> that = (AbstractMessage<?>) o;

        if(!id.equals(that.id)) return false;
        return storage.equals(that.storage);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + storage.hashCode();
        return result;
    }
}
