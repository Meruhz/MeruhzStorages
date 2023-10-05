package codes.meruhz.storages.data.providers;

import codes.meruhz.storages.data.Message;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.utils.LocaleUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class MessageProvider implements Message {

    private final @NotNull Map<@NotNull Locale, @NotNull BaseComponent @NotNull []> contents = new LinkedHashMap<>();

    private final @NotNull Storage storage;
    private final @NotNull String id;

    public MessageProvider(@NotNull Storage storage, @NotNull String id) {
        this.storage = storage;
        this.id = id;
    }

    public @NotNull Map<@NotNull Locale, @NotNull BaseComponent @NotNull []> getContents() {
        return this.contents;
    }

    @Override
    public @NotNull Storage getStorage() {
        return this.storage;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull Locale[] getLocales() {
        return this.getContents().keySet().toArray(new Locale[0]);
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale) {
        try {
            return this.getContents().get(locale);

        } catch (NullPointerException ignored) {
            try {
                return this.getContents().get(this.getStorage().getDefaultLocale());

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find specified locale '" + LocaleUtils.toString(locale) + "' and the default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "'");
            }
        }
    }

    @Override
    public void addContent(@NotNull Locale locale, @NotNull BaseComponent @NotNull [] content) {
        this.getContents().put(locale, content);
        this.getStorage().getMessages().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        MessageProvider that = (MessageProvider) o;

        if(!storage.equals(that.storage)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = storage.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
