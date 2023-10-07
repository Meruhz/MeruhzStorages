package com.storages.legacy.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.storages.core.data.Message;
import com.storages.core.data.Storage;
import com.storages.core.serializer.Serializer;
import com.storages.core.utils.LocaleUtils;
import com.storages.legacy.data.LegacyMessage;
import com.storages.legacy.data.LegacyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class LegacyStorageSerializer implements Serializer<Storage<String>> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage<String> storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", LocaleUtils.toString(storage.getDefaultLocale()));

        @NotNull JsonObject messagesJson = new JsonObject();
        for(Message<String> message : storage.getMessages()) {
            @NotNull LegacyMessage legacyMessage = (LegacyMessage) message;
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();

            for(Map.Entry<Locale, String> entrySet : legacyMessage.getContents().entrySet()) {
                contentJson.addProperty(LocaleUtils.toString(entrySet.getKey()), entrySet.getValue());
            }

            messageJson.add("content", contentJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull LegacyStorage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();
            @NotNull String name = json.get("name").getAsString();
            @NotNull Locale defaultLocale = LocaleUtils.toLocale(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull LegacyStorage storage = new LegacyStorage(name, defaultLocale);

            for(Map.Entry<String, JsonElement> messageEntry : messagesJson.entrySet()) {
                @NotNull String messageId = messageEntry.getKey();
                @NotNull JsonObject messageJson = messageEntry.getValue().getAsJsonObject();
                @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

                @NotNull LegacyMessage legacyMessage = new LegacyMessage(storage, messageId);

                for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                    legacyMessage.addContent(LocaleUtils.toLocale(entrySet.getKey()), entrySet.getValue().getAsString());
                }

                storage.getMessages().add(legacyMessage);
            }

            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserializing legacy storage json: " + element, throwable);
        }
    }
}