package com.storages.core.developers.provider;

import com.storages.core.StoragesCore;
import com.storages.core.data.Storage;
import com.storages.core.developers.StorageApi;
import com.storages.core.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class StorageApiProvider<M, L> implements StorageApi<M, L> {

    private final @NotNull Serializer<Storage<M, L>> serializer;
    private final @NotNull Set<@NotNull Storage<M, L>> storages;

    protected boolean loaded;

    public StorageApiProvider(@NotNull Serializer<Storage<M, L>> serializer) {
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
