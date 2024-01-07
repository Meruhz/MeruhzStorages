package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.MessageStorage;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractLanguageApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class StringLanguageApi extends AbstractLanguageApi {

    private static final @NotNull StringUtils STRING_UTILS = ((StringUtils) LanguageStorage.getMessageUtils());

    @Override
    public @NotNull JsonElement serialize(@NotNull MessageStorage<?> messageStorage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", messageStorage.getName());
        json.addProperty("default locale", messageStorage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<?> message : messageStorage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(MessageContent<?> content : message.getContents()) {
                @NotNull StringContent stringContent = (StringContent) content;

                if(!stringContent.isArrayText()) {
                    contentJson.addProperty(STRING_UTILS.localeToString(stringContent.getLocale()), stringContent.getText());

                } else {
                    @NotNull JsonArray contentArray = new JsonArray();

                    stringContent.getAsArrayText().forEach(contentArray::add);
                    contentJson.add(STRING_UTILS.localeToString(content.getLocale()), contentArray);
                }
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull MessageStorage<?> deserialize(@NotNull JsonElement jsonElement) {
        try {
            @NotNull JsonObject json = jsonElement.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = STRING_UTILS.stringToLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull StringStorage storage = new StringStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull StringMessage message = new StringMessage(id, storage);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<String> arrayText = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayText.add(contentElement.getAsString());
                        }

                        message.addContent(new StringContent(message, STRING_UTILS.stringToLocale(locale), arrayText));

                    } else {
                        message.addContent(new StringContent(message, STRING_UTILS.stringToLocale(locale), content.getAsString()));
                    }
                });

                storage.getMessages().add(message);
            });

            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to deserialize storage: " + jsonElement);
        }

    }
}
