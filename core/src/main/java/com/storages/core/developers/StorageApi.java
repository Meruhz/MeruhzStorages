package com.storages.core.developers;

import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public interface StorageApi<M> {

    @NotNull Set<@NotNull Storage<M>> getStorages();
    @NotNull Serializer<Storage<M>> getSerializer();

    default @NotNull Optional<Storage<M>> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @NotNull Storage<M> createStorage(@NotNull String name, @NotNull Locale defaultLocale);

    @NotNull Message<M> createMessage(@NotNull Storage<M> storage, @NotNull String id);


    boolean isLoaded();

    void unload();

    void load();
}
