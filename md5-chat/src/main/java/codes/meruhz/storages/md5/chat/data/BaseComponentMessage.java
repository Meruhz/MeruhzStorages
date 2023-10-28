package codes.meruhz.storages.md5.chat.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.data.impl.AbstractMessage;
import codes.meruhz.storages.md5.chat.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class BaseComponentMessage extends AbstractMessage<BaseComponent[], Locale> {

    public BaseComponentMessage(@NotNull BaseComponentStorage storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull BaseComponent @NotNull [] replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return ComponentUtils.replace(super.getText(locale), replaces);
    }

    @Override
    public @NotNull List<@NotNull BaseComponent @NotNull []> replaceArray(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return super.getArrayText(locale).stream().map(text -> ComponentUtils.replace(text, replaces)).collect(Collectors.toList());
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return ComponentUtils.getText(this.replace(locale, replaces));
    }

    public @NotNull List<String> getLegacyArray(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return ComponentUtils.getArrayText(this.replaceArray(locale, replaces));
    }
}
