package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.MessageContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * This abstract class provides a base implementation for the {@link MessageContent} interface.
 * It includes the essential properties such as the associated message, locale, text, and array text.
 *
 * @param <T> The type of text content used in messages, expected to be non-null.
 */
public abstract class AbstractMessageContent<T> implements MessageContent<T> {

    private final @NotNull Message<@NotNull T> message;
    private final @NotNull Locale locale;

    private final @Nullable T text;
    private final @Nullable List<@NotNull T> arrayText;

    /**
     * Constructs an AbstractMessageContent with the specified message, locale, and text content.
     *
     * @param message The message to which the content belongs.
     * @param locale  The locale of the content.
     * @param text    The text content.
     */
    public AbstractMessageContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull T text) {
        this.message = message;
        this.locale = locale;
        this.text = text;
        this.arrayText = null;
        this.getMessage().addContent(this);
    }

    /**
     * Constructs an AbstractMessageContent with the specified message, locale, and array text content.
     *
     * @param message   The message to which the content belongs.
     * @param locale    The locale of the content.
     * @param arrayText The array text content.
     */
    public AbstractMessageContent(@NotNull Message<@NotNull T> message, @NotNull Locale locale, @NotNull List<@NotNull T> arrayText) {
        this.message = message;
        this.locale = locale;
        this.text = null;
        this.arrayText = arrayText;
        this.getMessage().addContent(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Message<@NotNull T> getMessage() {
        return this.message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Locale getLocale() {
        return this.locale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T getText() {
        return Optional.ofNullable(this.text).orElseThrow(() -> new NullPointerException("Invalid text"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<@NotNull T> getAsArrayText() {
        return Optional.ofNullable(this.arrayText).orElseThrow(() -> new NullPointerException("Invalid array text"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArrayText() {
        return this.text == null && this.arrayText != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMessageContent<?> that = (AbstractMessageContent<?>) o;

        if (!message.equals(that.message)) return false;
        return locale.equals(that.locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + locale.hashCode();
        return result;
    }
}
