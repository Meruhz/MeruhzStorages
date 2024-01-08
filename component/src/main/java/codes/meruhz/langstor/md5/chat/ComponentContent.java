package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageContent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * This class represents the content of a message containing BungeeCord's BaseComponent[]. It extends
 * AbstractMessageContent and provides methods for replacing text and array text with placeholders.
 *
 * @see AbstractMessageContent
 */
public class ComponentContent extends AbstractMessageContent<BaseComponent[]> {

    /**
     * Constructs a ComponentContent with the specified message, locale, and text.
     *
     * @param message The message to which this content belongs.
     * @param locale  The locale for this content.
     * @param text    The text represented as BaseComponent[].
     */
    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, BaseComponent @NotNull [] text) {
        super(message, locale, text);
    }

    /**
     * Constructs a ComponentContent with the specified message, locale, and array text.
     *
     * @param message   The message to which this content belongs.
     * @param locale    The locale for this content.
     * @param arrayText The array text represented as a list of BaseComponent[].
     */
    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, @NotNull List<BaseComponent @NotNull []> arrayText) {
        super(message, locale, arrayText);
    }

    /**
     * Replaces placeholders in the text with the provided values.
     *
     * @param replaces The values to replace the placeholders with.
     * @return The updated text after replacement.
     */
    @Override
    public BaseComponent @NotNull [] replace(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).replaceText(super.getText(), replaces);
    }

    /**
     * Replaces placeholders in the array text with the provided values.
     *
     * @param replaces The values to replace the placeholders with.
     * @return The updated array text after replacement.
     */
    @Override
    public @NotNull List<BaseComponent @NotNull []> replaceArray(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).replaceArray(super.getAsArrayText(), replaces);
    }
}
