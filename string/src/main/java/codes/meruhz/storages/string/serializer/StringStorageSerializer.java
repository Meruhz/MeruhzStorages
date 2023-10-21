package codes.meruhz.storages.string.serializer;

import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializer.Serializer;
import codes.meruhz.storages.core.utils.LocaleUtils;
import codes.meruhz.storages.string.data.StringMessage;
import codes.meruhz.storages.string.data.StringStorage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class StringStorageSerializer implements Serializer<Storage<String, Locale>> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage<String, Locale> storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<String, Locale> message : storage.getMessages()) {
            @NotNull StringMessage stringMessage = (StringMessage) message;
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(Map.Entry<Locale, String> entrySet : stringMessage.getContents().entrySet()) {
                contentJson.addProperty(LocaleUtils.toString(entrySet.getKey()), entrySet.getValue());
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull Storage<String, Locale> deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = LocaleUtils.toLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull StringStorage storage = new StringStorage(name, defaultLocale);

            for(Map.Entry<String, JsonElement> messageEntry : messagesJson.entrySet()) {
                @NotNull String messageId = messageEntry.getKey();
                @NotNull JsonObject messageJson = messageEntry.getValue().getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull StringMessage message = new StringMessage(storage, messageId);

                for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                    message.addContent(LocaleUtils.toLocale(entrySet.getKey()), entrySet.getValue().getAsString());
                }

                storage.getMessages().add(message);
            }

            storage.load();
            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserializing string storage json: " + element, throwable);
        }
    }
}
