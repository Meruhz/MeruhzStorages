package com.storages.core.data;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface Message<M> {

    @NotNull Storage<M> getStorage();

    @NotNull String getId();

    @NotNull Locale @NotNull [] getLocales();

    @NotNull M getText(@NotNull Locale locale);

    @NotNull M replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces);

    void addContent(@NotNull Locale locale, @NotNull M content);
}
