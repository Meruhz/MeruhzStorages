package codes.meruhz.storages.string.serializer;

import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializer.Serializer;
import codes.meruhz.storages.core.utils.MessageUtils;
import codes.meruhz.storages.string.data.StringMessage;
import codes.meruhz.storages.string.data.StringStorage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class StringStorageSerializer implements Serializer<Storage<String, Locale>> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage<String, Locale> storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        storage.getMessages().forEach(message -> {
            @NotNull StringMessage stringMessage = (StringMessage) message;
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            stringMessage.getContents().forEach((locale, content) -> contentJson.addProperty(MessageUtils.serializeLocale(locale), content));

            stringMessage.getArrayContents().forEach((locale, arrayContent) -> {
                @NotNull JsonArray contentArray = new JsonArray();

                arrayContent.forEach(contentArray::add);
                contentJson.add(MessageUtils.serializeLocale(locale), contentArray);
            });

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        });

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull Storage<String, Locale> deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = MessageUtils.deserializeLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull StringStorage storage = new StringStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull StringMessage message = new StringMessage(storage, id);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<String> arrayContents = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayContents.add(contentElement.getAsString());
                        }

                        message.addArrayContent(MessageUtils.deserializeLocale(locale), arrayContents);

                    } else {
                        message.addContent(MessageUtils.deserializeLocale(locale), content.getAsString());
                    }
                });

                storage.getMessages().add(message);
            });

            storage.load();
            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserializing string storage json: " + element, throwable);
        }
    }
}
