package com.storages.component.serializers;

import codes.laivy.mlanguage.lang.Locale;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.storages.component.BaseComponentStorages;
import com.storages.component.data.BaseComponentMessage;
import com.storages.component.data.BaseComponentStorage;
import com.storages.component.utils.ComponentUtils;
import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.serializer.Serializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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

            for(Map.Entry<Locale, BaseComponent[]> entrySet : baseComponentMessage.getContents().entrySet()) {
                contentJson.addProperty(entrySet.getKey().toString(), ComponentUtils.getText(entrySet.getValue()));
            }

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

            for(Map.Entry<String, JsonElement> messageEntry : messagesJson.entrySet()) {
                @NotNull String messageId = messageEntry.getKey();
                @NotNull JsonObject messageJson = messageEntry.getValue().getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull BaseComponentMessage message = new BaseComponentMessage(storage, messageId);

                for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                    message.addContent(Locale.valueOf(entrySet.getKey()), new BaseComponent[] { new TextComponent(ComponentUtils.getText(new TextComponent(entrySet.getValue().getAsString())))});
                }

                storage.getMessages().add(message);
            }

            BaseComponentStorages.storages().getBaseComponentStorageApi().getStorages().add(storage);
            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserializing base component storage json: " + element, throwable);
        }
    }
}