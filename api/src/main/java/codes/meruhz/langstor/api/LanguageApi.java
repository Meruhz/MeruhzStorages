package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

/**
 * The LanguageApi interface represents an API for managing language-related functionalities.
 * It extends the StorageSerializer interface to provide serialization capabilities.
 */
public non-sealed interface LanguageApi extends StorageSerializer {

    /**
     * Retrieves a collection of message storages associated with the language API.
     *
     * @return A collection of non-null MessageStorage instances.
     */
    @NotNull Collection<@NotNull MessageStorage<?>> getStorages();

    /**
     * Retrieves a specific message storage by its name.
     *
     * @param name The name of the desired message storage.
     * @return An optional containing the message storage if found, otherwise an empty optional.
     */
    default @NotNull Optional<MessageStorage<?>> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    /**
     * Retrieves a message by its unique identifier from any of the associated message storages.
     *
     * @param id The unique identifier of the desired message.
     * @return An optional containing the message if found, otherwise an empty optional.
     */
    default @NotNull Optional<? extends Message<?>> getMessage(@NotNull String id) {
        return this.getStorages().stream().map(storage -> storage.getMessage(id)).findFirst();
    }
}
