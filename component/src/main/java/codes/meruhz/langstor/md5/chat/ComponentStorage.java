package codes.meruhz.langstor.md5.chat;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageStorage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ComponentStorage extends AbstractMessageStorage<BaseComponent[]> {

    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<BaseComponent[]>> messages) {
        super(name, defaultLocale, messages);
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyText(super.getMessage(id).getText(locale, replaces));
    }

    public @NotNull String getLegacyText(@NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyText(super.getMessage(id).getText(replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull String id, @NotNull Locale locale, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyArray(super.getMessage(id).getArrayText(locale, replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull String id, @NotNull Object... replaces) {
        return ((ComponentUtils) LanguageStorage.langstor().getMessageUtils()).getLegacyArray(super.getMessage(id).getArrayText(replaces));
    }
}
