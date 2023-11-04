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
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        try {
            return ComponentUtils.replace(super.getContents().get(locale), replaces);

        } catch (NullPointerException ex) {
            try {
                return ComponentUtils.replace(super.getContents().get(super.getStorage().getDefaultLocale()), replaces);

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }

    @Override
    public @NotNull List<@NotNull BaseComponent @NotNull []> getArrayText(Locale locale, @NotNull Object @NotNull [] replaces) {
        if(!this.isArrayText(locale)) {
            throw new IllegalStateException("Message '" + super.getId() + "' from locale '" + locale + "' is not an array text");

        } else try {
            return super.getArrayContents().get(locale).stream().map(text -> ComponentUtils.replace(text, replaces)).collect(Collectors.toList());

        } catch (NullPointerException ex) {

            try {
                return super.getArrayContents().get(super.getStorage().getDefaultLocale()).stream().map(text -> ComponentUtils.replace(text, replaces)).collect(Collectors.toList());

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return ComponentUtils.getText(this.getText(locale, replaces));
    }

    public @NotNull List<String> getLegacyArray(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return ComponentUtils.getArrayText(this.getArrayText(locale, replaces));
    }
}
