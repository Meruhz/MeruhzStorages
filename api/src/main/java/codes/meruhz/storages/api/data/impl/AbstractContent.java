package codes.meruhz.storages.api.data.impl;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.Content;
import codes.meruhz.storages.api.data.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractContent<T> implements Content<T> {

    private final @NotNull Message<@NotNull T> message;
    private final @NotNull Locale locale;

    private final @Nullable T text;
    private final @Nullable List<@NotNull T> arrayText;

    private final boolean array;

    public AbstractContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull T text) {
        this.message = message;
        this.locale = locale;
        this.text = text;
        this.arrayText = null;
        this.array = false;
    }

    public AbstractContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull List<@NotNull T> arrayText) {
        this.message = message;
        this.locale = locale;
        this.text = null;
        this.arrayText = arrayText;
        this.array = true;
    }

    @Override
    public @NotNull Message<@NotNull T> getMessage() {
        return this.message;
    }

    @Override
    public @NotNull Locale getLocale() {
        return this.locale;
    }

    @Override
    public @NotNull T getText() {
        return Optional.ofNullable(this.text).orElseThrow(() -> new NullPointerException("Locale '" + this.getLocale() + "' for message '" + this.getMessage().getId() + "' is not an normal text"));
    }

    @Override
    public boolean isArrayText() {
        return this.array && this.arrayText != null;
    }

    @Override
    public @NotNull List<@NotNull T> getAsArrayText() {
        return Optional.ofNullable(this.arrayText).orElseThrow(() -> new NullPointerException("Locale '" + this.getLocale() + "' for message '" + this.getMessage().getId() + "' is not an array text"));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractContent<?> that = (AbstractContent<?>) o;

        if(!message.equals(that.message)) return false;
        return locale == that.locale;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + locale.hashCode();
        return result;
    }
}
