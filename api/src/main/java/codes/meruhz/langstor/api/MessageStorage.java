package codes.meruhz.langstor.api;

import codes.meruhz.langstor.api.utils.model.JsonElementConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public interface MessageStorage<T> {

    @NotNull String getName();

    @NotNull Locale getDefaultLocale();

    @NotNull JsonElementConfiguration getJsonModel();

    @NotNull Collection<@NotNull Message<T>> getMessages();

    default @NotNull Message<T> getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().orElseThrow(() -> new NullPointerException("Message with id '" + id + "' could not be found at storage '" + this.getName() + "'"));
    }

    default @NotNull T getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getContent(locale).replace(replaces);
    }

    default @NotNull T getText(@NotNull String id, @NotNull Object... replaces) {
        return this.getText(this.getDefaultLocale(), id, replaces);
    }

    default @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return this.getMessage(id).getContent(locale).replaceArray(replaces);
    }

    default @NotNull List<@NotNull T> getArrayText(@NotNull String id, @NotNull Object... replaces) {
        return this.getArrayText(this.getDefaultLocale(), id, replaces);
    }
}
