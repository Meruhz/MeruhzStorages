package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Storage {

    @NotNull String getName();

    @NotNull LocaleEnum getDefaultLocale();

    @NotNull Set<@NotNull Message> getMessages();

    default @NotNull Message getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().orElseThrow(() -> new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'"));
    }

    default @NotNull Message getMessage(@NotNull LocaleEnum locale, @NotNull String id) {
        @NotNull Message message = this.getMessage(id);

        if(!message.getLocales().containsKey(locale)) {
            throw new NullPointerException("Could not be found locale '" + locale + "' for message '" + id + "' at storage '" + this.getName() + "'");
        }

        return message;
    }

    default @NotNull BaseComponent @NotNull [] getContent(@NotNull LocaleEnum locale, @NotNull String id, @NotNull Object... replaces) {
        try {
            return this.getMessage(locale, id).replace(locale, replaces);

        } catch (NullPointerException ignored) {
            return this.getMessage(this.getDefaultLocale(), id).replace(locale, replaces);
        }
    }

    default @NotNull String getLegacyContent(@NotNull LocaleEnum locale, @NotNull String id, @NotNull Object... replaces) {
        return BaseComponent.toLegacyText(this.getContent(locale, id, replaces));
    }
}
