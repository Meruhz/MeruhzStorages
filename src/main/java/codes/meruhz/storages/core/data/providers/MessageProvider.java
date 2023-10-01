package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class MessageProvider implements Message {

    private final @NotNull String id;
    private final @NotNull Set<@NotNull LocalizedMessage> localizedMessages;

    public MessageProvider(@NotNull String id, @NotNull LocalizedMessage... localizedMessages) {
        this.id = id;
        this.localizedMessages = new LinkedHashSet<>(Arrays.asList(localizedMessages));
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

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
