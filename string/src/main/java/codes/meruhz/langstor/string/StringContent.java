package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.providers.AbstractMessageContent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class StringContent extends AbstractMessageContent<String> {

    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull String text) {
        super(message, locale, text);
    }

    public StringContent(@NotNull StringMessage message, @NotNull Locale locale, @NotNull List<@NotNull String> arrayText) {
        super(message, locale, arrayText);
    }

    @Override
    public @NotNull String replace(Object @NotNull [] replaces) {
        return ((StringUtils) LanguageStorage.getMessageUtils()).replaceText(super.getText(), replaces);
    }

    @Override
    public @NotNull List<@NotNull String> replaceArray(Object @NotNull [] replaces) {
        return ((StringUtils) LanguageStorage.getMessageUtils()).replaceArray(super.getAsArrayText(), replaces);
    }
}
