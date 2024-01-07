package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public non-sealed interface LanguageApi extends StorageSerializer {

    @NotNull Collection<@NotNull MessageStorage<?>> getStorages();

    default @NotNull Optional<MessageStorage<?>> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    default @NotNull Optional<? extends Message<?>> getMessage(@NotNull String id) {
        return this.getStorages().stream().map(storage -> storage.getMessage(id)).findFirst();
    }
}