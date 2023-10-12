package codes.meruhz.storages.data;

import org.jetbrains.annotations.NotNull;

public interface Message<M, L> {

    @NotNull Storage<M, L> getStorage();

    @NotNull String getId();

    @NotNull L @NotNull [] getLocales();

    @NotNull M getText(@NotNull L locale);

    @NotNull M replace(@NotNull L locale, @NotNull Object @NotNull [] replaces);

    void addContent(@NotNull L locale, @NotNull M content);
}
