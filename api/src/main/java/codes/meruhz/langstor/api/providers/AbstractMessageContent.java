package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public abstract class AbstractMessageContent<T> implements MessageContent<T> {

    private final @NotNull Message<@NotNull T> message;
    private final @NotNull Locale locale;

    private final @Nullable T text;
    private final @Nullable List<@NotNull T> arrayText;

    public AbstractMessageContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull T text) {
        this.message = message;
        this.locale = locale;
        this.text = text;
        this.arrayText = null;
    }

    public AbstractMessageContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull List<@NotNull T> arrayText) {
        this.message = message;
        this.locale = locale;
        this.text = null;
        this.arrayText = arrayText;
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
        return Optional.ofNullable(this.text).orElseThrow(() -> new NullPointerException("Invalid text"));
    }

    @Override
    public @NotNull List<@NotNull T> getAsArrayText() {
        return Optional.ofNullable(this.arrayText).orElseThrow(() -> new NullPointerException("Invalid array text"));
    }

    @Override
    public boolean isArrayText() {
        return this.text == null && this.arrayText != null;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractMessageContent<?> that = (AbstractMessageContent<?>) o;

        if(!message.equals(that.message)) return false;
        return locale.equals(that.locale);
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + locale.hashCode();
        return result;
    }
}
