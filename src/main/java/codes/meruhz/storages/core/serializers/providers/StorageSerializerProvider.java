package codes.meruhz.storages.core.serializers.providers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.data.providers.LocalizedMessageProvider;
import codes.meruhz.storages.core.data.providers.MessageProvider;
import codes.meruhz.storages.core.data.providers.StorageProvider;
import codes.meruhz.storages.core.serializers.Serializer;
import codes.meruhz.storages.utils.ComponentUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@ApiStatus.Internal
public class StorageSerializerProvider implements Serializer<Storage> {

    @Override
    public @NotNull JsonElement serialize(@NotNull Storage storage) {
        @NotNull JsonObject json = new JsonObject();
        json.addProperty("name", storage.getName());
        json.addProperty("default locale", storage.getDefaultLocale().toString());

        @NotNull JsonObject messagesJson = new JsonObject();

        for(Message message : storage.getMessages()) {
            @NotNull JsonObject messageJson = new JsonObject();
            @NotNull JsonObject contentJson = new JsonObject();
            @NotNull JsonObject replacementsJson = new JsonObject();

            for(LocalizedMessage localizedMessage : message.getLocalizedMessages()) {
                @NotNull String locale = localizedMessage.getLocale().toString();

                contentJson.addProperty(locale, ComponentUtils.serialize(localizedMessage.getContent()));

                if(!localizedMessage.getReplacements().isEmpty()) {
                    @NotNull JsonArray replacementsArray = new JsonArray();

                    for(Object replace : localizedMessage.getReplacements()) {
                        replacementsArray.add(replace.toString());
                    }

                    replacementsJson.add(locale, replacementsArray);
                }
            }

            messageJson.add("content", contentJson);
            messageJson.add("replacements", replacementsJson);
            messagesJson.add(message.getId(), messageJson);
        }

        json.add("messages", messagesJson);
        return json;
    }

    @Override
    public @NotNull Storage deserialize(@NotNull JsonElement element) {
        try {
            @NotNull JsonObject json = element.getAsJsonObject();
            @NotNull String name = json.get("name").getAsString();
            @NotNull LocaleEnum defaultLocale = LocaleEnum.valueOf(json.get("default locale").getAsString());

            @NotNull JsonObject messagesJson = json.getAsJsonObject("messages");
            @NotNull String messageId = messagesJson.entrySet().iterator().next().getKey();

            @NotNull JsonObject messageJson = messagesJson.getAsJsonObject(messageId);
            @NotNull JsonObject contentJson = messageJson.getAsJsonObject("content");

            @NotNull Storage storage = new StorageProvider(name, defaultLocale);
            @NotNull Message message = new MessageProvider(messageId);

            for(Map.Entry<String, JsonElement> entrySet : contentJson.entrySet()) {
                @NotNull LocalizedMessage localizedMessage = new LocalizedMessageProvider(message, LocaleEnum.valueOf(entrySet.getKey()), ComponentSerializer.parse(entrySet.getValue().getAsString()));

                if(messageJson.has("replacements")) {
                    @NotNull JsonObject replacementsJson = messageJson.getAsJsonObject("replacements");
                    @NotNull JsonArray replacementsArray = replacementsJson.getAsJsonArray(entrySet.getKey());

                    for(JsonElement replaceElement : replacementsArray) {
                        localizedMessage.replace(replaceElement.getAsString());
                    }
                }

                message.getLocalizedMessages().add(localizedMessage);
            }
            storage.getMessages().add(message);
            return storage;

        } catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while deserialize storage json: " + element, throwable);
        }
    }
}
