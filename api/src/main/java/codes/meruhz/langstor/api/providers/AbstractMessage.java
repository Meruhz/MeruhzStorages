package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.MessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This abstract class provides a base implementation for the {@link Message} interface.
 * It includes the essential properties such as message ID, associated message storage, and message contents.
 *
 * @param <T> The type of text content used in messages, expected to be non-null.
 */
public abstract class AbstractMessage<T> implements Message<T> {

    private final @NotNull String id;
    private final @NotNull MessageStorage<T> storage;
    private final @NotNull Set<@NotNull MessageContent<T>> contents;

    /**
     * Constructs an AbstractMessage with the specified ID and associated message storage.
     *
     * @param id      The unique identifier for the message.
     * @param storage The message storage to which the message belongs.
     */
    public AbstractMessage(@NotNull String id, @NotNull MessageStorage<T> storage) {
        this(id, storage, new LinkedHashSet<>());
    }

    /**
     * Constructs an AbstractMessage with the specified ID, associated message storage, and initial set of contents.
     *
     * @param id       The unique identifier for the message.
     * @param storage  The message storage to which the message belongs.
     * @param contents The initial set of message contents associated with the message.
     */
    public AbstractMessage(@NotNull String id, @NotNull MessageStorage<T> storage, @NotNull Set<@NotNull MessageContent<T>> contents) {
        this.id = id;
        this.storage = storage;
        this.contents = contents;
        this.getStorage().getMessages().add(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MessageStorage<T> getStorage() {
        return this.storage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<@NotNull MessageContent<T>> getContents() {
        return this.contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMessage<?> that = (AbstractMessage<?>) o;

        if (!id.equals(that.id)) return false;
        return storage.equals(that.storage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + storage.hashCode();
        return result;
    }
}
