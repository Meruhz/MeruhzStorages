package codes.meruhz.storages.md5.chat.utils;

import com.google.gson.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ComponentUtils {

    private ComponentUtils() {
    }

    public static @NotNull String serialize(@NotNull BaseComponent component) {
        if (component instanceof TextComponent) {
            TextComponent text = (TextComponent)component;
            if (!text.hasFormatting() && (text.getExtra() == null || text.getExtra().isEmpty())) {
                JsonObject object = new JsonObject();
                object.addProperty("text", text.getText());
                return object.toString();
            }
        }

        return ComponentSerializer.toString(component);
    }

    public static @NotNull String serialize(BaseComponent... components) {
        if (components.length == 0) {
            throw new JsonParseException("Empty array of base components");
        } else if (components.length == 1) {
            return serialize(components[0]);
        } else {
            JsonArray array = new JsonArray();

            for(BaseComponent component : components) {
                String serialized = serialize(component);

                try {
                    array.add(JsonParser.parseString(serialized));
                } catch (JsonSyntaxException var8) {
                    array.add(serialized);
                }
            }

            return array.toString();
        }
    }

    public static @NotNull String getText(@NotNull BaseComponent... components) {
        @NotNull StringBuilder str = new StringBuilder();

        for(BaseComponent component : components) {
            str.append(component.toLegacyText());
        }

        return str.toString().startsWith("§f") ? str.toString().replaceFirst("§f", "") : str.toString();
    }

    public static @NotNull List<String> getArrayText(@NotNull List<@NotNull BaseComponent @NotNull []> components) {
        return components.stream().flatMap(Arrays::stream).map(ComponentUtils::getText).collect(Collectors.toList());
    }

    public static @NotNull BaseComponent @NotNull [] replace(@NotNull BaseComponent @NotNull [] components, @NotNull Object @NotNull [] replaces) {
        @NotNull String serialized = ComponentUtils.serialize(components);

        for(Object replace : replaces) {
            serialized = serialized.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(serialized);
    }

    public static boolean isLegacyText(@NotNull BaseComponent... components) {
        try {
            @NotNull JsonObject message = JsonParser.parseString(ComponentUtils.getText(components)).getAsJsonObject();
            @NotNull String[] attributes = {"text", "bold", "italic", "underlined", "strikethrough", "obfuscated", "color", "clickEvent", "hoverEvent", "insertion", "font"};

            for(String attribute : attributes) {
                return message.has(attribute) && ComponentSerializer.parse(message.toString()) == null;
            }

            return false;

        } catch (JsonSyntaxException e) {
            return true;
        }
    }
}