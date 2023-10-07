package com.storages.core.developers.provider;

import com.storages.core.data.Storage;
import com.storages.core.developers.StorageApi;
import com.storages.core.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class StorageApiProvider<M> implements StorageApi<M> {

    private final @NotNull Serializer<Storage<M>> serializer;
    private final @NotNull Set<@NotNull Storage<M>> storages;

    protected boolean loaded;

    public StorageApiProvider(@NotNull Serializer<Storage<M>> serializer) {
        this.serializer = serializer;
        this.storages = new LinkedHashSet<>();
    }

    @Override
    public @NotNull Set<@NotNull Storage<M>> getStorages() {
        return this.storages;
    }

    @Override
    public @NotNull Serializer<Storage<M>> getSerializer() {
        return this.serializer;
    }

    @Override
    public @NotNull Optional<Storage<M>> getStorage(@NotNull String name) {
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

        for(Storage<M> storage : this.getStorages()) {
            storage.save();
        }

        this.getStorages().clear();

        this.loaded = false;
    }
}
