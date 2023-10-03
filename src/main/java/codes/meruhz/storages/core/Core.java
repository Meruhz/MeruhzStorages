package codes.meruhz.storages.core;

import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.developers.StorageApi;
import codes.meruhz.storages.core.serializers.Serializer;
import org.jetbrains.annotations.NotNull;

public interface Core {

    @NotNull Serializer<Storage> getSerializer();

    @NotNull StorageApi getApi();

    boolean isLoaded();

    void unload();

    void load();
}
