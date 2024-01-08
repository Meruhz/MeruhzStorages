package codes.meruhz.langstor.api;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

/**
 * This sealed interface defines a storage serializer responsible for serializing
 * and deserializing {@link MessageStorage} instances to and from JSON representations.
 * Implementing classes are required to be part of the sealed hierarchy specified by
 * the 'permits' clause.
 */
public sealed interface StorageSerializer permits LanguageApi {

    /**
     * Serializes a {@link MessageStorage} instance to a {@link JsonElement}.
     *
     * @param messageStorage The message storage to serialize.
     * @return A non-null instance of {@link JsonElement} representing the serialized content.
     */
    @NotNull JsonElement serialize(@NotNull MessageStorage<?> messageStorage);

    /**
     * Deserializes a {@link JsonElement} to a {@link MessageStorage} instance.
     *
     * @param jsonElement The JSON element to deserialize.
     * @return A non-null instance of {@link MessageStorage} representing the deserialized content.
     */
    @NotNull MessageStorage<?> deserialize(@NotNull JsonElement jsonElement);
}
