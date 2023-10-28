package codes.meruhz.storages.md5.chat.serializer;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializer.Serializer;
import codes.meruhz.storages.md5.chat.data.BaseComponentMessage;
import codes.meruhz.storages.md5.chat.data.BaseComponentStorage;
import codes.meruhz.storages.md5.chat.utils.ComponentUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class BaseComponentStorageSerializer implements Serializer<Storage<BaseComponent[], Locale>> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage<BaseComponent[], Locale> storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<BaseComponent[], Locale> message : storage.getMessages()) {
            @NotNull BaseComponentMessage baseComponentMessage = (BaseComponentMessage) message;
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            baseComponentMessage.getContents().forEach((locale, content) -> contentJson.addProperty(locale.toString(), ComponentUtils.getText(content)));

            baseComponentMessage.getArrayContents().forEach((locale, arrayContent) -> {
                @NotNull JsonArray contentArray = new JsonArray();

                ComponentUtils.getArrayText(arrayContent).forEach(contentArray::add);
                contentJson.add(locale.toString(), contentArray);
            });

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull BaseComponentStorage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = Locale.valueOf(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull BaseComponentStorage storage = new BaseComponentStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull BaseComponentMessage message = new BaseComponentMessage(storage, id);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<BaseComponent[]> arrayContents = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayContents.add(new BaseComponent[] { new TextComponent(ComponentUtils.getText(new TextComponent(contentElement.getAsString()))) });
                        }

                        message.addArrayContent(Locale.valueOf(locale), arrayContents);

                    } else {
                        message.addContent(Locale.valueOf(locale), new BaseComponent[] {new TextComponent(ComponentUtils.getText(new TextComponent(content.getAsString())))});
                    }
                });

                storage.getMessages().add(message);
            });

            storage.load();
            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserializing base component storage json: " + element, throwable);
        }
    }
}
