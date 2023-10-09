package com.storages.component.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.storages.component.BaseComponentStorages;
import com.storages.component.data.BaseComponentMessage;
import com.storages.component.data.BaseComponentStorage;
import com.storages.component.utils.ComponentUtils;
import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.serializer.Serializer;
import com.storages.core.utils.LocaleUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class BaseComponentStorageSerializer implements Serializer<Storage<BaseComponent[]>> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage<BaseComponent[]> storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", LocaleUtils.toString(storage.getDefaultLocale()));

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<BaseComponent[]> message : storage.getMessages()) {
            @NotNull BaseComponentMessage baseComponentMessage = (BaseComponentMessage) message;
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(Map.Entry<Locale, BaseComponent[]> entrySet : baseComponentMessage.getContents().entrySet()) {
                // TODO [09/10/2023]: FIX LEGACY TEXT READER

                @NotNull String text = ComponentUtils.getText(entrySet.getValue());

                if(!ComponentUtils.isLegacyText(new BaseComponent[]{ new TextComponent(text)})) {
                    text = ComponentUtils.serialize(entrySet.getValue());
                }

                contentJson.addProperty(LocaleUtils.toString(entrySet.getKey()), text);
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
            @NotNull Locale defaultLocale = LocaleUtils.toLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull BaseComponentStorage storage = new BaseComponentStorage(name, defaultLocale);

            for(Map.Entry<String, JsonElement> messageEntry : messagesJson.entrySet()) {
                @NotNull String messageId = messageEntry.getKey();
                @NotNull JsonObject messageJson = messageEntry.getValue().getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull BaseComponentMessage message = new BaseComponentMessage(storage, messageId);

                for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                    @NotNull BaseComponent @NotNull [] text;

                    try {
                        text = ComponentSerializer.parse(entrySet.getValue().getAsString());

                    } catch (JsonSyntaxException e) {
                        text = new BaseComponent[] { new TextComponent(entrySet.getValue().getAsString()) };
                    }

                    message.addContent(LocaleUtils.toLocale(entrySet.getKey()), text);
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