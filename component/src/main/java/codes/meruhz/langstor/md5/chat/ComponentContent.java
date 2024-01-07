package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageContent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ComponentContent extends AbstractMessageContent<BaseComponent[]> {

    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, BaseComponent @NotNull [] text) {
        super(message, locale, text);
    }

    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, @NotNull List<BaseComponent @NotNull []> arrayText) {
        super(message, locale, arrayText);
    }

    @Override
    public BaseComponent @NotNull [] replace(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).replaceText(super.getText(), replaces);
    }

    @Override
    public @NotNull List<BaseComponent @NotNull []> replaceArray(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.getMessageUtils()).replaceArray(super.getAsArrayText(), replaces);
    }
}
