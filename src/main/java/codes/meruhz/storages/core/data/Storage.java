package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Storage {

    @NotNull String getName();

    @NotNull LocaleEnum getDefaultLocale();

    @NotNull JsonConfiguration getConfiguration();

    @NotNull Set<@NotNull Message> getMessages();

    default @NotNull Message getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().orElseThrow(() -> new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'"));
    }

    default @NotNull LocalizedMessage getLocalizedMessage(@NotNull LocaleEnum locale, @NotNull String id) {
        return this.getMessage(id).getLocalizedMessages().stream().filter(lc -> lc.getLocale().equals(locale)).findFirst().orElseThrow(() -> new NullPointerException("Could not be found locale '" + locale + "' for message '" + id + "' at storage '" + this.getName() + "'"));
    }

    default @NotNull BaseComponent[] getText(@NotNull LocaleEnum locale, @NotNull String id) {
        try {
            return this.getLocalizedMessage(locale, id).getContent();

        } catch (NullPointerException ignored) {
            return this.getLocalizedMessage(this.getDefaultLocale(), id).getContent();
        }
    }

    default @NotNull String getLegacyText(@NotNull LocaleEnum locale, @NotNull String id) {
        return BaseComponent.toLegacyText(this.getText(locale, id));
    }
}
