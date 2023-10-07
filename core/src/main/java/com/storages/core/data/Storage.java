package com.storages.core.data;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface Storage<M> {

    @NotNull String getName();

    @NotNull Locale getDefaultLocale();

    @NotNull Type getType();

    @NotNull Set<@NotNull Message<M>> getMessages();

    default @NotNull Optional<Message<M>> getMessage(@NotNull String id) {
        return this.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst();
    }

    @NotNull M getText(@NotNull Locale locale, @NotNull String id, @NotNull Objects... replaces);

    void save();

    enum Type {

        BASE_COMPONENT, LEGACY;
    }
}
