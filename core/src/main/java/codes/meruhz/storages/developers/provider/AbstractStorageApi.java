package codes.meruhz.storages.developers.provider;

import codes.meruhz.storages.StoragesCore;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.developers.StorageApi;
import codes.meruhz.storages.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractStorageApi<M, L> implements StorageApi<M, L> {

    private final @NotNull Serializer<Storage<M, L>> serializer;
    private final @NotNull Set<@NotNull Storage<M, L>> storages;

    protected boolean loaded;

    public AbstractStorageApi(@NotNull Serializer<Storage<M, L>> serializer) {
        this.serializer = serializer;
        this.storages = new LinkedHashSet<>();
    }

    @Override
    public @NotNull Set<@NotNull Storage<M, L>> getStorages() {
        return this.storages;
    }

    @Override
    public @NotNull Serializer<Storage<M, L>> getSerializer() {
        return this.serializer;
    }

    @Override
    public @NotNull Optional<Storage<M, L>> getStorage(@NotNull String name) {
        return StorageApi.super.getStorage(name);
    }

    @Override
    public boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public void unload() {
        if(!this.isLoaded()) {
            throw new IllegalStateException("MeruhzStorages API is not loaded");
        }

        for(Storage<M, L> storage : this.getStorages()) {

            try {
                storage.save();

            } catch (Throwable throwable) {
                StoragesCore.getLogger().severe("Failed to unload storage '" + storage.getName() + "'");
                throwable.printStackTrace();
            }
        }

        this.getStorages().clear();
        this.loaded = false;
    }
}
