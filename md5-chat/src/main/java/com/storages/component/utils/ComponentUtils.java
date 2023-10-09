package com.storages.component.utils;

import com.google.gson.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

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

    public static @NotNull String getText(BaseComponent... components) {
        StringBuilder str = new StringBuilder();

        for(BaseComponent component : components) {
            str.append(component.toLegacyText());
        }

        return str.toString().startsWith("§f") ? str.toString().replaceFirst("§f", "") : str.toString();
    }

    public static boolean isLegacyText(@NotNull BaseComponent @NotNull [] components) {
        @NotNull String text = ComponentUtils.getText(components);

        try {
            ComponentSerializer.parse(text);
            return false;

        } catch (JsonParseException e) {
            return true;
        }
    }
}