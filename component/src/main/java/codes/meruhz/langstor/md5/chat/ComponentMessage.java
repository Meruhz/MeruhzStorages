package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.MessageContent;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ComponentMessage extends AbstractMessage<BaseComponent[]> {

    public ComponentMessage(@NotNull String id, @NotNull ComponentStorage storage) {
        super(id, storage);
    }

    public ComponentMessage(@NotNull String id, @NotNull ComponentStorage storage, @NotNull Set<@NotNull MessageContent<BaseComponent[]>> messageContents) {
        super(id, storage, messageContents);
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyText(super.getText(locale, replaces));
    }

    public @NotNull String getLegacyText(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyText(super.getText(replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyArray(super.getArrayText(locale, replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(Object @NotNull [] replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyArray(super.getArrayText(replaces));
    }
}
