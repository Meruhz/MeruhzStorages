package codes.meruhz.storages.core.serializers.providers;

import codes.meruhz.storages.MeruhzStorages;
import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializers.Serializer;
import codes.meruhz.storages.utils.ComponentUtils;
import codes.meruhz.storages.utils.LocaleUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

@ApiStatus.Internal
public class StorageSerializerProvider implements Serializer<Storage> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", LocaleUtils.toString(storage.getDefaultLocale()));

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message message : storage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(LocalizedMessage localizedMessage : message.getLocalizedMessages()) {
                contentJson.addProperty(LocaleUtils.toString(localizedMessage.getLocale()), ComponentUtils.serialize(localizedMessage.getContent()));
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull Storage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();
            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = LocaleUtils.toLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull Storage storage = MeruhzStorages.getInstance().getCore().getApi().createStorage(name, defaultLocale);

            for(Map.Entry<String, JsonElement> messageEntry : messagesJson.entrySet()) {
                @NotNull String messageId = messageEntry.getKey();
                @NotNull JsonObject messageJson = messageEntry.getValue().getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull Message message = MeruhzStorages.getInstance().getCore().getApi().createMessage(storage, messageId);
                for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                    MeruhzStorages.getInstance().getCore().getApi().createLocalizedMessage(message, LocaleUtils.toLocale(entrySet.getKey()), ComponentSerializer.parse(entrySet.getValue().getAsString()));
                }

                storage.getMessages().add(message);
            }

            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserialize storage json: " + element, throwable);
        }
    }
}
