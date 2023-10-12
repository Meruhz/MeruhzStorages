package codes.meruhz.storages.developers;

import codes.meruhz.storages.data.Message;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface StorageApi<M, L> {

    @NotNull Set<@NotNull Storage<M, L>> getStorages();
    @NotNull Serializer<Storage<M, L>> getSerializer();

    default @NotNull Optional<Storage<M, L>> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @NotNull Storage<M, L> createStorage(@NotNull String name, @NotNull L defaultLocale);

    @NotNull Message<M, L> createMessage(@NotNull Storage<M, L> storage, @NotNull String id);

    boolean isLoaded();

    void unload();

    void load();
}
