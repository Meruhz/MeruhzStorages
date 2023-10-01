package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LocalizedMessageProvider implements LocalizedMessage {

    private final @NotNull Message message;
    private final @NotNull LocaleEnum locale;
    private final @NotNull BaseComponent[] content;
    private final @NotNull List<@NotNull Object> replacements;

    public LocalizedMessageProvider(@NotNull Message message, @NotNull LocaleEnum locale, @NotNull BaseComponent[] content, @NotNull Object... replacements) {
        this.message = message;
        this.locale = locale;
        this.content = content;
        this.replacements = new LinkedList<>(Arrays.asList(replacements));
        this.getMessage().getLocalizedMessages().add(this);
    }

    @Override
    public @NotNull Message getMessage() {
        return this.message;
    }

    @Override
    public @NotNull LocaleEnum getLocale() {
        return this.locale;
    }

    @Override
    public @NotNull BaseComponent[] getContent() {
        return this.content;
    }

    @Override
    public @NotNull BaseComponent[] replace(@NotNull Object... replaces) {
        @NotNull String text = ComponentUtils.serialize(this.getContent());

        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
            this.getReplacements().add(replace);
        }

        return ComponentSerializer.parse(text);
    }

    @Override
    public @NotNull List<@NotNull Object> getReplacements() {
        return this.replacements;
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
