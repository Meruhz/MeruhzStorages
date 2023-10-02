package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Storage {

    @NotNull String getName();

    @NotNull LocaleEnum getDefaultLocale();

    @NotNull Set<@NotNull Message> getMessages();

    @NotNull BaseComponent @NotNull [] getText(@NotNull LocaleEnum locale, @NotNull String id, @NotNull Object... replaces);

    default @NotNull String getLegacyText(@NotNull LocaleEnum locale, @NotNull String id, @NotNull Object... replaces) {
        return TextComponent.toLegacyText(this.getText(locale, id, replaces));
    }

    void load();
}
