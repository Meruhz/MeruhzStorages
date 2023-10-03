package codes.meruhz.storages.core.data;

import codes.meruhz.storages.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Set;

public interface Message {

    @NotNull Storage getStorage();

    @NotNull String getId();

    @NotNull Set<@NotNull LocalizedMessage> getLocalizedMessages();

    default @NotNull LocalizedMessage getMessage(@NotNull Locale locale) {
        return this.getLocalizedMessages().stream().filter(lc -> lc.getLocale().equals(locale)).findFirst().orElseThrow(() -> new NullPointerException("Could not be found a message with id '" + this.getId() + "' and locale '" + locale + "' at storage '" + this.getStorage().getName()));
    }

    default @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale) {
        try {
            return this.getMessage(locale).getContent();

        } catch (NullPointerException ignored) {
            try {
                return this.getMessage(this.getStorage().getDefaultLocale()).getContent();

            } catch (NullPointerException e) {
                throw new RuntimeException("Unable to return the specified Locale '" + locale + "', and a default was not found at storage '" + this.getStorage().getName() + "'", e);
            }
        }
    }

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
}
