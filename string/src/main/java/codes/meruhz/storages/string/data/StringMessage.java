package codes.meruhz.storages.string.data;

import codes.meruhz.storages.core.data.impl.AbstractMessage;
import codes.meruhz.storages.core.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringMessage extends AbstractMessage<String, Locale> {

    public StringMessage(@NotNull StringStorage storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull String getText(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        try {
            return MessageUtils.replace(super.getContents().get(locale), replaces);

        } catch (NullPointerException ex) {
            try {
                return MessageUtils.replace(super.getContents().get(super.getStorage().getDefaultLocale()), replaces);

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }

    @Override
    public @NotNull List<@NotNull String> getArrayText(Locale locale, @NotNull Object @NotNull [] replaces) {
        if(!this.isArrayText(locale)) {
            throw new IllegalStateException("Message '" + super.getId() + "' from locale '" + locale + "' is not an array text");

        } else try {
            return super.getArrayContents().get(locale).stream().map(text -> MessageUtils.replace(text, replaces)).collect(Collectors.toList());

        } catch (NullPointerException ex) {

            try {
                return super.getArrayContents().get(super.getStorage().getDefaultLocale()).stream().map(text -> MessageUtils.replace(text, replaces)).collect(Collectors.toList());

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }
}
