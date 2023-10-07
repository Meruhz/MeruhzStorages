package com.storages.legacy.data;

import com.storages.core.data.providers.MessageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LegacyMessage extends MessageProvider<String> {

    public LegacyMessage(@NotNull LegacyStorage storage, @NotNull String id) {
        super(storage, id);
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
