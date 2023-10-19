package codes.meruhz.storages.data;

import codes.meruhz.storages.utils.Loader;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface Storage<M, L> extends Loader {

    @NotNull String getName();

    @NotNull L getDefaultLocale();

    @NotNull Set<@NotNull Message<M, L>> getMessages();

    default @NotNull Optional<@NotNull Message<M, L>> getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst();
    }

    @NotNull M getText(@NotNull L locale, @NotNull String id, @NotNull Object... replaces);
}