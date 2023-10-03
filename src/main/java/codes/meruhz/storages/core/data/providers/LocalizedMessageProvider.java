package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LocalizedMessageProvider implements LocalizedMessage {

    private final @NotNull Message message;
    private final @NotNull Locale locale;
    private final @NotNull BaseComponent @NotNull [] content;

    public LocalizedMessageProvider(@NotNull Message message, @NotNull Locale locale, @NotNull BaseComponent @NotNull [] content) {
        this.message = message;
        this.locale = locale;
        this.content = content;
        message.getLocalizedMessages().add(this);
    }

    @Override
    public @NotNull Message getMessage() {
        return this.message;
    }

    @Override
    public @NotNull Locale getLocale() {
        return this.locale;
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getContent() {
        return this.content;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        LocalizedMessageProvider that = (LocalizedMessageProvider) o;

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
