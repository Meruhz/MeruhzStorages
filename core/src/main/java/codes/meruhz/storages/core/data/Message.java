package codes.meruhz.storages.core.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Message<M, L> {

    @NotNull String getId();

    @NotNull Storage<M, L> getStorage();

    @NotNull M getText(@NotNull L locale, @NotNull Object @NotNull [] replaces);

    @NotNull List<M> getArrayText(L locale, @NotNull Object @NotNull [] replaces);

    boolean isArrayText(@NotNull L locale);

    void addArrayContent(@NotNull L locale, @NotNull List<M> content);

    void addContent(@NotNull L locale, @NotNull M content);
}
