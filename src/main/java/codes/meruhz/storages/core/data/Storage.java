package codes.meruhz.storages.core.data;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Set;

public interface Storage {

    @NotNull String getName();

    @NotNull Locale getDefaultLocale();

    @NotNull Set<@NotNull Message> getMessages();

    @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces);

    default @NotNull String getLegacyText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return TextComponent.toLegacyText(this.getText(locale, id, replaces));
    }

    void load();
}
