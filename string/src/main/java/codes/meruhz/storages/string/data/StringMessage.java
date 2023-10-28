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
    public @NotNull String replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return MessageUtils.replace(super.getText(locale), replaces);
    }

    @Override
    public @NotNull List<String> replaceArray(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        return super.getArrayText(locale).stream().map(text -> MessageUtils.replace(text, replaces)).collect(Collectors.toList());
    }
}
