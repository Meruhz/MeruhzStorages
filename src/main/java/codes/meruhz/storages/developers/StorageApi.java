package codes.meruhz.storages.developers;

import codes.meruhz.storages.data.Message;
import codes.meruhz.storages.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public interface StorageApi {

    @NotNull Set<@NotNull Storage> getStorages();

    default @NotNull Optional<Storage> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @NotNull Storage createStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Message... messages);

    @NotNull Message createMessage(@NotNull Storage storage, @NotNull String id);
}