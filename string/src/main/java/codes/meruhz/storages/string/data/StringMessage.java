package codes.meruhz.storages.string.data;

import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.data.impl.AbstractMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StringMessage extends AbstractMessage<String, Locale> {

    public StringMessage(@NotNull Storage<String, Locale> storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull Locale @NotNull [] getLocales() {
        return super.getContents().keySet().toArray(new Locale[0]);
    }

    @Override
    public @NotNull String replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        @NotNull String text = super.getText(locale);

        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
        }

        return text;
    }
}
