package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public interface MessageContent<T> {

    @NotNull Message<@NotNull T> getMessage();

    @NotNull Locale getLocale();

    @NotNull T getText();

    @NotNull T replace(Object @NotNull [] replaces);

    @NotNull List<@NotNull T> replaceArray(Object @NotNull [] replaces);

    @NotNull List<@NotNull T> getAsArrayText();

    boolean isArrayText();
}
