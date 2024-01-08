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

/**
 * This class provides utility methods for handling BungeeCord's BaseComponent[] messages.
 * It implements the MessageUtils interface and includes methods for replacing text and arrays,
 * serializing components, retrieving legacy text, and getting a legacy text array.
 *
 * @see MessageUtils
 */
public class ComponentUtils implements MessageUtils<BaseComponent[]> {

    /**
     * Replaces placeholders in the given BaseComponent[] text with the provided objects.
     *
     * @param text     The BaseComponent[] text to be replaced.
     * @param replaces The objects to replace placeholders in the text.
     * @return The BaseComponent[] text with replaced placeholders.
     */
    @Override
    public BaseComponent @NotNull [] replaceText(BaseComponent @NotNull [] text, Object @NotNull [] replaces) {
        @NotNull String serialized = this.serialize(text);

        for(Object replace : replaces) {
            serialized = serialized.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(serialized);
    }

    /**
     * Replaces placeholders in each BaseComponent[] array within the provided list with the provided objects.
     *
     * @param arrayText The list of BaseComponent[] arrays to be replaced.
     * @param replaces  The objects to replace placeholders in the arrays.
     * @return A list of BaseComponent[] arrays with replaced placeholders.
     */
    @Override
    public @NotNull List<BaseComponent @NotNull []> replaceArray(@NotNull List<BaseComponent @NotNull []> arrayText, Object @NotNull [] replaces) {
        return arrayText.stream().map(components -> this.replaceText(components, replaces)).collect(Collectors.toList());
    }

    /**
     * Serializes a single BaseComponent into a JSON string.
     *
     * @param component The BaseComponent to be serialized.
     * @return The JSON string representation of the serialized BaseComponent.
     */
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

    /**
     * Serializes an array of BaseComponents into a JSON array string.
     *
     * @param components The array of BaseComponents to be serialized.
     * @return The JSON array string representation of the serialized BaseComponents.
     */
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

    /**
     * Retrieves the legacy text from an array of BaseComponents.
     *
     * @param components The array of BaseComponents.
     * @return The legacy text as a string.
     */
    public @NotNull String getLegacyText(@NotNull BaseComponent... components) {
        @NotNull StringBuilder stringBuilder = new StringBuilder();

        for(BaseComponent baseComponent : components) {
            stringBuilder.append(baseComponent.toLegacyText());
        }

        return stringBuilder.toString().startsWith("§f") ? stringBuilder.toString().replaceFirst("§f", "") : stringBuilder.toString();
    }

    /**
     * Retrieves a list of legacy texts from a list of BaseComponent[] arrays.
     *
     * @param baseComponents The list of BaseComponent[] arrays.
     * @return A list of legacy texts as strings.
     */
    public @NotNull List<@NotNull String> getLegacyArray(@NotNull List<BaseComponent @NotNull []> baseComponents) {
        return baseComponents.stream().flatMap(Arrays::stream).map(this::getLegacyText).collect(Collectors.toList());
    }
}
