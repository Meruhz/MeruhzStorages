package codes.meruhz.storages.string.serializer;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.Content;
import codes.meruhz.storages.api.data.Message;
import codes.meruhz.storages.api.serializer.StorageSerializer;
import codes.meruhz.storages.string.data.StringContent;
import codes.meruhz.storages.string.data.StringMessage;
import codes.meruhz.storages.string.data.StringStorage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class StringStorageSerializer implements StorageSerializer<StringStorage> {

    @Override
    public @NotNull JsonElement serialize(@NotNull StringStorage storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<String> message : storage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(Content<String> content : message.getContents()) {
                @NotNull StringContent stringContent = (StringContent) content;

                if(!stringContent.isArrayText()) {
                    contentJson.addProperty(content.getLocale().toString(), content.getText());

                } else {
                    @NotNull JsonArray contentArray = new JsonArray();

                    stringContent.getAsArrayText().forEach(contentArray::add);
                    contentJson.add(content.getLocale().toString(), contentArray);
                }
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull StringStorage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = Locale.valueOf(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull StringStorage storage = new StringStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull StringMessage message = new StringMessage(storage, id);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<String> arrayText = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayText.add(contentElement.getAsString());
                        }

                        message.addContent(new StringContent(message, Locale.valueOf(locale), arrayText));

                    } else {
                        message.addContent(new StringContent(message, Locale.valueOf(locale), content.getAsString()));
                    }
                });

                storage.getMessages().add(message);
            });

            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to deserialize storage: " + element);
        }
    }
}
