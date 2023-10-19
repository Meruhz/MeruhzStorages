package codes.meruhz.storages.serializer;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public interface Serializer<T> {

    @NotNull JsonElement serialize(@NotNull T obj);

    @NotNull T deserialize(@NotNull JsonElement element);
}
