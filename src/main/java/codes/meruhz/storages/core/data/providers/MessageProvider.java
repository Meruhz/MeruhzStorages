package codes.meruhz.storages.core.data.providers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.Message;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageProvider implements Message {

    private final @NotNull String id;
    private final @NotNull List<@NotNull Object> replacements;
    private final @NotNull Map<@NotNull LocaleEnum, @NotNull BaseComponent @NotNull []> locales;

    public MessageProvider(@NotNull String id, @NotNull Map<@NotNull LocaleEnum, @NotNull BaseComponent @NotNull []> locales, @NotNull Object... replaces) {
        this.id = id;
        this.locales = locales;
        this.replacements = new LinkedList<>(Arrays.asList(replaces));
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull List<@NotNull Object> getReplacements() {
        return this.replacements;
    }

    @Override
    public @NotNull Map<@NotNull LocaleEnum, @NotNull BaseComponent @NotNull []> getLocales() {
        return this.locales;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        MessageProvider that = (MessageProvider) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
