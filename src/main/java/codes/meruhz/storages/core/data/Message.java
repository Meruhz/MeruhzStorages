package codes.meruhz.storages.core.data;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Message {

    @NotNull String getId();

    @NotNull Set<@NotNull LocalizedMessage> getLocalizedMessages();
}
