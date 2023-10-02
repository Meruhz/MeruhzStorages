package codes.meruhz.storages.core.developers;

import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializers.Serializer;
import org.jetbrains.annotations.NotNull;

public interface StorageApiCore {

    @NotNull Serializer<Storage> getSerializer();

    @NotNull StorageApi getApi();

    boolean isLoaded();

    void unload();

    void load();
}
