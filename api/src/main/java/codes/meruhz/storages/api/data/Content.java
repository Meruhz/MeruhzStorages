package codes.meruhz.storages.api.data;

import codes.laivy.mlanguage.lang.Locale;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Content<T> {

    @NotNull Message<@NotNull T> getMessage();

    @NotNull Locale getLocale();

    @NotNull T getText();

    boolean isArrayText();

    @NotNull T replace(Object @NotNull [] replaces);

    @NotNull List<@NotNull T> getAsArrayText();
}
