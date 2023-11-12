package codes.meruhz.storages.api.data;

import codes.laivy.mlanguage.lang.Locale;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface Message<T> {

    @NotNull String getId();

    @NotNull Storage<@NotNull T> getStorage();

    @NotNull Collection<@NotNull Content<@NotNull T>> getContents();

    @NotNull T getText(@NotNull Locale locale, Object @NotNull [] replaces);

    @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, Object @NotNull [] replaces);

    void addContent(@NotNull Content<@NotNull T> content);
}
