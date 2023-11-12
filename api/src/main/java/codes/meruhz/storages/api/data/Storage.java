package codes.meruhz.storages.api.data;

import codes.laivy.mlanguage.lang.Locale;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface Storage<T> {

    @NotNull File getSource();

    @NotNull String getName();

    @NotNull Locale getDefaultLocale();

    @NotNull Set<@NotNull Message<T>> getMessages();

    default @NotNull Message<@NotNull T> getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().orElseThrow(() -> new NullPointerException("Message '" + id + "' could not be found at storage '" + this.getName() + "'"));
    }

    default @NotNull T getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getText(locale, replaces);
    }

    default @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getArrayText(locale, replaces);
    }
}
