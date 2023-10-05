package codes.meruhz.storages.data;

import codes.meruhz.storages.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface Message {

    @NotNull Storage getStorage();

    @NotNull String getId();

    @NotNull Locale[] getLocales();

    @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale);

    default @NotNull String getLegacyText(@NotNull Locale locale) {
        return TextComponent.toLegacyText(this.getText(locale));
    }

    default @NotNull BaseComponent @NotNull [] replace(@NotNull Locale locale, @NotNull Object... replaces) {
        @NotNull String text = ComponentUtils.serialize(this.getText(locale));

        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(text);
    }

    void addContent(@NotNull Locale locale, @NotNull BaseComponent @NotNull [] content);
}
