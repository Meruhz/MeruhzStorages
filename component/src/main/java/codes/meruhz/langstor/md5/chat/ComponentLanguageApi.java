package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.MessageStorage;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractLanguageApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ComponentLanguageApi extends AbstractLanguageApi {

    private static final @NotNull ComponentUtils COMPONENT_UTILS = ((ComponentUtils) LanguageStorage.getMessageUtils());

    @Override
    public @NotNull JsonElement serialize(@NotNull MessageStorage<?> messageStorage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", messageStorage.getName());
        json.addProperty("default locale", COMPONENT_UTILS.localeToString(messageStorage.getDefaultLocale()));

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<?> message : messageStorage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(MessageContent<?> content : message.getContents()) {
                @NotNull ComponentContent componentContent = (ComponentContent) content;

                if(!componentContent.isArrayText()) {
                    contentJson.addProperty(COMPONENT_UTILS.localeToString(componentContent.getLocale()), COMPONENT_UTILS.getLegacyText(componentContent.getText()));

                } else {
                    @NotNull JsonArray contentArray = new JsonArray();

                    COMPONENT_UTILS.getLegacyArray(componentContent.getAsArrayText()).forEach(contentArray::add);
                    contentJson.add(COMPONENT_UTILS.localeToString(componentContent.getLocale()), contentArray);
                }
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull ComponentStorage deserialize(@NotNull JsonElement jsonElement) {
        try {
            @NotNull JsonObject json = jsonElement.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = COMPONENT_UTILS.stringToLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull ComponentStorage storage = new ComponentStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull ComponentMessage message = new ComponentMessage(id, storage);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<BaseComponent[]> arrayText = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayText.add(new BaseComponent[] { new TextComponent(COMPONENT_UTILS.getLegacyText(new TextComponent(contentElement.getAsString())))});
                        }

                        message.addContent(new ComponentContent(message, COMPONENT_UTILS.stringToLocale(locale), arrayText));

                    } else {
                        message.addContent(new ComponentContent(message, COMPONENT_UTILS.stringToLocale(locale), new BaseComponent[] {new TextComponent(COMPONENT_UTILS.getLegacyText(new TextComponent(content.getAsString())))}));
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
