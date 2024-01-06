package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public interface MessageUtils<T> {

    @NotNull String localeToString(@NotNull Locale locale);

    @NotNull Locale stringToLocale(@NotNull String string);

    @NotNull T replaceText(@NotNull String text, Object @NotNull [] replaces);

    @NotNull List<@NotNull T> replaceArray(@NotNull List<@NotNull T> arrayText, Object @NotNull [] replaces);
}
