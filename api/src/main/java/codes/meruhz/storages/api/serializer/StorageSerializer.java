package codes.meruhz.storages.api.serializer;

import codes.meruhz.storages.api.data.Storage;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public interface StorageSerializer<T extends Storage<?>> {

    @NotNull JsonElement serialize(@NotNull T storage);

    @NotNull T deserialize(@NotNull JsonElement element);
}
