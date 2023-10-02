package codes.meruhz.storages.core.developers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface StorageApi {

    @NotNull Set<@NotNull Storage> getStorages();

    default @NotNull Optional<Storage> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @NotNull Storage createStorage(@NotNull String name, @NotNull LocaleEnum defaultLocale, @NotNull Message... messages);

    @NotNull Message createMessage(@NotNull Storage storage, @NotNull String id);

    @NotNull LocalizedMessage createLocalizedMessage(@NotNull Message message, @NotNull LocaleEnum locale, @NotNull BaseComponent @NotNull [] content);
}
