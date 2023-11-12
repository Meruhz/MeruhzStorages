package codes.meruhz.storages.string.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.impl.AbstractContent;
import codes.meruhz.storages.api.utils.ContentUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StringContent extends AbstractContent<String> {

    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull String text) {
        super(message, locale, text);
    }

    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull List<@NotNull String> arrayText) {
        super(message, locale, arrayText);
    }

    @Override
    public @NotNull String replace(Object @NotNull [] replaces) {
        return ContentUtils.replace(super.getText(), replaces);
    }
}
