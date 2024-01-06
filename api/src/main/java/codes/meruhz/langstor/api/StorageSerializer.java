package codes.meruhz.langstor.api;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public sealed interface StorageSerializer permits LanguageApi {

    @NotNull JsonElement serialize(@NotNull MessageStorage<?> messageStorage);

    @NotNull MessageStorage<?> deserialize(@NotNull JsonElement jsonElement);
}
