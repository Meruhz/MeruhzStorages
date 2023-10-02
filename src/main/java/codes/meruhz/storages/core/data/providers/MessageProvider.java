package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

public class MessageProvider implements Message {

    private final @NotNull Set<@NotNull LocalizedMessage> localizedMessages = new LinkedHashSet<>();

    private final @NotNull Storage storage;
    private final @NotNull String id;

    public MessageProvider(@NotNull Storage storage, @NotNull String id) {
        this.storage = storage;
        this.id = id;

        this.getStorage().getMessages().add(this);
    }

    @Override
    public @NotNull Storage getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull Set<@NotNull LocalizedMessage> getLocalizedMessages() {
        return this.localizedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        MessageProvider that = (MessageProvider) o;

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
