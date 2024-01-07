package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.MessageUtils;
import com.google.gson.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentUtils implements MessageUtils<BaseComponent[]> {

    @Override
    public BaseComponent @NotNull [] replaceText(BaseComponent @NotNull [] text, Object @NotNull [] replaces) {
        @NotNull String serialized = this.serialize(text);

        for(Object replace : replaces) {
            serialized = serialized.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(serialized);
    }

    @Override
    public @NotNull List<BaseComponent @NotNull []> replaceArray(@NotNull List<BaseComponent @NotNull []> arrayText, Object @NotNull [] replaces) {
        return arrayText.stream().map(components -> this.replaceText(components, replaces)).collect(Collectors.toList());
    }

    public @NotNull String serializeComponent(@NotNull BaseComponent component) {
        if(component instanceof TextComponent textComponent) {

            if(!textComponent.hasFormatting() && (textComponent.getExtra() == null || textComponent.getExtra().isEmpty())) {
                @NotNull JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("text", textComponent.getText());

                return jsonObject.toString();
            }
        }

        return ComponentSerializer.toString(component);
    }

    public @NotNull String serialize(@NotNull BaseComponent... components) {
        if(components.length == 0) {
            throw new JsonParseException("Empty array of base components");

        } else if(components.length == 1) {
            return this.serializeComponent(components[0]);

        } else {
            @NotNull JsonArray jsonArray = new JsonArray();

            for(BaseComponent baseComponent : components) {
                @NotNull String serialized = this.serializeComponent(baseComponent);

                try {
                    jsonArray.add(JsonParser.parseString(serialized));

                } catch (JsonSyntaxException e) {
                    jsonArray.add(serialized);
                }
            }

            return jsonArray.toString();
        }
    }

    public @NotNull String getLegacyText(@NotNull BaseComponent... components) {
        @NotNull StringBuilder stringBuilder = new StringBuilder();

        for(BaseComponent baseComponent : components) {
            stringBuilder.append(baseComponent.toLegacyText());
        }

        return stringBuilder.toString().startsWith("§f") ? stringBuilder.toString().replaceFirst("§f", "") : stringBuilder.toString();
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull List<BaseComponent @NotNull []> baseComponents) {
        return baseComponents.stream().flatMap(Arrays::stream).map(this::getLegacyText).collect(Collectors.toList());
    }
}
