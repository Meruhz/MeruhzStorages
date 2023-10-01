package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Message {

    @NotNull String getId();

    @NotNull List<@NotNull Object> getReplacements();

    @NotNull Map<@NotNull LocaleEnum, @NotNull BaseComponent @NotNull []> getLocales();

    default @NotNull BaseComponent @NotNull [] getContent(@NotNull LocaleEnum locale) {
        if(!this.getLocales().containsKey(locale)) {
            throw new NullPointerException("Could not be found locale '" + locale + "' for message '" + this.getId() + "'");
        }

        return this.getLocales().get(locale);
    }

    default @NotNull BaseComponent @NotNull [] replace(@NotNull LocaleEnum locale, @NotNull Object... replaces) {
        @NotNull String text = ComponentUtils.serialize(this.getContent(locale));

        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
            this.getReplacements().add(replace);
        }

        return ComponentSerializer.parse(text);
    }
}
