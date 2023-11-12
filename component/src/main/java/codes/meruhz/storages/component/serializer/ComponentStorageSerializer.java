package codes.meruhz.storages.component.serializer;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.Content;
import codes.meruhz.storages.api.data.Message;
import codes.meruhz.storages.api.serializer.StorageSerializer;
import codes.meruhz.storages.component.data.ComponentContent;
import codes.meruhz.storages.component.data.ComponentMessage;
import codes.meruhz.storages.component.data.ComponentStorage;
import codes.meruhz.storages.component.utils.ComponentUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ComponentStorageSerializer implements StorageSerializer<ComponentStorage> {

    @Override
    public @NotNull JsonElement serialize(@NotNull ComponentStorage storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<BaseComponent[]> message : storage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(Content<BaseComponent[]> content : message.getContents()) {
                @NotNull ComponentContent componentContent = (ComponentContent) content;

                if(!componentContent.isArrayText()) {
                    contentJson.addProperty(componentContent.getLocale().toString(), ComponentUtils.getText(componentContent.getText()));

                } else {
                    @NotNull JsonArray contentArray = new JsonArray();

                    ComponentUtils.getArrayText(componentContent.getAsArrayText()).forEach(contentArray::add);
                    contentJson.add(componentContent.getLocale().toString(), contentArray);
                }
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull ComponentStorage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();

            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = Locale.valueOf(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull ComponentStorage storage = new ComponentStorage(name, defaultLocale);

            messagesJson.asMap().forEach((id, messageElement) -> {
                @NotNull JsonObject messageJson = messageElement.getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull ComponentMessage message = new ComponentMessage(storage, id);

                contentJson.asMap().forEach((locale, content) -> {

                    if(content.isJsonArray()) {
                        @NotNull List<BaseComponent[]> arrayText = new LinkedList<>();

                        for(JsonElement contentElement : (JsonArray) content) {
                            arrayText.add(new BaseComponent[] {new TextComponent(ComponentUtils.getText(new TextComponent(contentElement.getAsString())))});
                        }

                        message.addContent(new ComponentContent(message, Locale.valueOf(locale), arrayText));

                    } else {
                        message.addContent(new ComponentContent(message, Locale.valueOf(locale), new BaseComponent[] {new TextComponent(ComponentUtils.getText(new TextComponent(content.getAsString())))}));
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
